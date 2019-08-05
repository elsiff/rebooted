package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-07-27.
 */
data class Size(
    val width: Float,
    val height: Float
) {
    fun toVector2f() = vec2f(width, height)
}

fun size(width: Float = 0f, height: Float = 0f) = Size(width, height)
fun size(width: Number, height: Number) = Size(width.toFloat(), height.toFloat())
