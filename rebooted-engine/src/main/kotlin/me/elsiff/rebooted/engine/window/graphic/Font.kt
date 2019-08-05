package me.elsiff.rebooted.engine.window.graphic

import me.elsiff.rebooted.engine.window.graphic.drawable.TextureRegion

/**
 * Created by elsiff on 2019-03-23.
 */
interface Font {
    val size: Float

    fun glyphOf(char: Char): TextureRegion
}