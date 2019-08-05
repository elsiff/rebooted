package me.elsiff.rebooted.engine.lwjgl.asset

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.elsiff.rebooted.engine.asset.AssetLoadRequest
import me.elsiff.rebooted.engine.asset.AssetLoader
import me.elsiff.rebooted.engine.asset.request.FontLoadRequest
import me.elsiff.rebooted.engine.lwjgl.LwjglEngine
import me.elsiff.rebooted.engine.lwjgl.window.graphic.GlFont
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable.GlTexture
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable.GlTextureRegion
import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.math.size
import me.elsiff.rebooted.engine.math.vec2f
import me.elsiff.rebooted.engine.window.graphic.drawable.Text
import java.awt.FontMetrics
import java.awt.RenderingHints
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.nio.file.Paths
import kotlin.math.max
import java.awt.Font as AwtFont

/**
 * Created by elsiff on 2019-03-25.
 */
class GlFontLoader(
    private val engine: LwjglEngine
) : AssetLoader {
    override suspend fun load(request: AssetLoadRequest): GlFont {
        require(request is FontLoadRequest)

        val filePath = request.filePath
        val fontSize = request.fontSize

        val awtFont = withContext(Dispatchers.IO) {
            AwtFont.createFont(AwtFont.TRUETYPE_FONT, Paths.get(filePath).toFile())
                .deriveFont(fontSize)
        }

        val glyphRegions = mutableMapOf<Char, GlyphRegion>()
        val image = withContext(Dispatchers.IO) {
            createFontImage(awtFont, request.antiAlias, glyphRegions)
        }

        val texture = GlTexture.createFrom(image)
        val regions = glyphRegions.mapValues {
            GlTextureRegion(
                texture,
                it.value.coordinate,
                it.value.size
            )
        }
        return GlFont(regions, fontSize)
    }

    private fun createFontImage(
        font: AwtFont,
        antiAlias: Boolean,
        glyphRegions: MutableMap<in Char, in GlyphRegion>
    ): BufferedImage {
        val fontMetrics = createFontMetrics(font, antiAlias)
        check(fontMetrics.height != 0) { "Height of a font must not be 0" }

        val fontImage = createEmptyFontImage(font, fontMetrics, antiAlias)
        val fontImageGraphics = fontImage.createGraphics()

        var x = 0f
        for (character in Text.CHARS) {
            val charImage = createCharImage(font, fontMetrics, character, antiAlias)
            val charWidth = charImage.width
            val charHeight = charImage.height

            val glyph = GlyphRegion(vec2f(x, charImage.height - charHeight), size(charWidth, charHeight))
            glyphRegions[character] = glyph

            fontImageGraphics.drawImage(charImage, x.toInt(), 0, null)
            x += glyph.size.width
        }

        /* Flip image Horizontal to get the origin to bottom left */
        val transform = AffineTransform.getScaleInstance(1.0, -1.0)
        transform.translate(0.0, (-fontImage.height).toDouble())
        val operation = AffineTransformOp(
            transform,
            AffineTransformOp.TYPE_NEAREST_NEIGHBOR
        )
        return operation.filter(fontImage, null)
    }

    private fun createFontMetrics(font: AwtFont, antiAlias: Boolean): FontMetrics {
        val tempImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val graphics = tempImage.createGraphics()
        if (antiAlias) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        }
        graphics.font = font
        val metrics = graphics.fontMetrics
        graphics.dispose()
        return metrics
    }

    private fun createEmptyFontImage(font: AwtFont, fontMetrics: FontMetrics, antiAlias: Boolean): BufferedImage {
        var imageWidth = 0
        var imageHeight = 0

        for (c in Text.CHARS) {
            val ch = createCharImage(font, fontMetrics, c, antiAlias)
            imageWidth += ch.width
            imageHeight = max(imageHeight, ch.height)
        }
        return BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
    }

    private fun createCharImage(
        font: AwtFont,
        fontMetrics: FontMetrics,
        character: Char,
        antiAlias: Boolean
    ): BufferedImage {
        val charWidth = fontMetrics.charWidth(character)
        val charHeight = fontMetrics.height

        val image = BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB)
        val graphics = image.createGraphics()
        if (antiAlias) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        }
        graphics.font = font
        graphics.paint = java.awt.Color.WHITE
        graphics.drawString(character.toString(), 0, fontMetrics.ascent)
        graphics.dispose()
        return image
    }

    private data class GlyphRegion(
        val coordinate: Vector2f,
        val size: Size
    )
}