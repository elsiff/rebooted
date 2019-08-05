package me.elsiff.rebooted.engine.lwjgl.window.graphic.drawer

import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.Batch
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable.GlTextureRegion
import me.elsiff.rebooted.engine.math.EPSILON
import me.elsiff.rebooted.engine.math.mutableVec2f
import me.elsiff.rebooted.engine.window.graphic.drawable.Drawable
import me.elsiff.rebooted.engine.window.graphic.drawable.Rectangle
import me.elsiff.rebooted.engine.window.graphic.drawable.Text
import kotlin.math.max

/**
 * Created by elsiff on 2019-03-23.
 */
class TextDrawer(
    private val textureRegionDrawer: GlTextureRegionDrawer,
    private val rectangleDrawer: RectangleDrawer
) : Drawer {
    override fun draw(thing: Drawable, batch: Batch, option: Drawable.DrawingOption) {
        require(thing is Text)

        val position = option.position
        val offset = mutableVec2f(0f, 0f)

        thing.block.traverse { string, style ->
            var maxHeight = style.font.size

            for (char in string) {
                when (char) {
                    Text.NEXT_LINE -> {
                        offset.y -= maxHeight
                        offset.x = 0f
                    }
                    else -> {
                        val glyph = style.font.glyphOf(char) as GlTextureRegion

                        if (style.backColor.alpha > EPSILON) {
                            rectangleDrawer.draw(
                                thing = Rectangle(
                                    style.backColor,
                                    glyph.size
                                ),
                                batch = batch,
                                option = Drawable.DrawingOption(
                                    position = position + offset,
                                    drawingNumber = option.drawingNumber
                                )
                            )
                        }

                        textureRegionDrawer.draw(
                            thing = glyph,
                            batch = batch,
                            option = GlTextureRegion.DrawingOption(
                                position = position + offset,
                                drawingNumber = option.drawingNumber,
                                color = style.color
                            )
                        )

                        maxHeight = max(maxHeight, glyph.size.height)
                        offset.x += glyph.size.width
                    }
                }
            }
        }
    }
}