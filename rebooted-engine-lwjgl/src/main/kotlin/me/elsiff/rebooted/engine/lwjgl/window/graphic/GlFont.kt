package me.elsiff.rebooted.engine.lwjgl.window.graphic

import me.elsiff.rebooted.engine.window.graphic.Font
import me.elsiff.rebooted.engine.window.graphic.drawable.TextureRegion

/**
 * Created by elsiff on 2019-03-23.
 */
class GlFont(
    private val glyphs: Map<Char, TextureRegion>,
    override val size: Float
) : Font {
    override fun glyphOf(char: Char): TextureRegion {
        return glyphs[char] ?: error("Not supported character")
    }
}