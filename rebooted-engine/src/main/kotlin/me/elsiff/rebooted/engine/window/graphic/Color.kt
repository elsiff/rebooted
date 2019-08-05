package me.elsiff.rebooted.engine.window.graphic

/**
 * Created by elsiff on 2019-02-27.
 */
data class Color(
    val red: Float,
    val green: Float,
    val blue: Float,
    val alpha: Float
) {
    constructor(red: Float, green: Float, blue: Float) : this(red, green, blue, 1f)

    companion object {
        val BLACK: Color =
            fromRGB(0x000000)
        val WHITE: Color =
            fromRGB(0xffffff)
        val TRANSPARENT = fromRGBA(0x00000000)

        fun fromRGB(rgb: Int): Color {
            val r = (rgb shr 16) and 0xff
            val g = (rgb shr 8) and 0xff
            val b = rgb and 0xff
            return fromRGBSpace(r, g, b)
        }

        fun fromRGBA(rgba: Int): Color {
            val r = (rgba shr 24) and 0xff
            val g = (rgba shr 16) and 0xff
            val b = (rgba shr 8) and 0xff
            val a = rgba and 0xff
            return fromRGBSpace(r, g, b, a)
        }

        fun fromRGBSpace(r: Int, g: Int, b: Int, a: Int = 255): Color =
            Color(r / 255f, g / 255f, b / 255f, a / 255f)
    }
}