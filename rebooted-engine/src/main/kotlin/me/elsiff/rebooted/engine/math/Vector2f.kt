package me.elsiff.rebooted.engine.math

import kotlin.math.sqrt

/**
 * Created by elsiff on 2019-02-28.
 */
interface Vector2f {
    val x: Float
    val y: Float

    fun length(): Float = sqrt(lengthSquared())
    fun lengthSquared(): Float = ((x * x) + (y * y))

    infix fun dot(other: Vector2f): Float = ((x * other.x) + (y * other.y))

    fun normalized(): Vector2f

    fun plus(incrementX: Float, incrementY: Float): Vector2f
    fun minus(decrementX: Float, decrementY: Float): Vector2f
    fun times(multiplierX: Float, multiplierY: Float): Vector2f
    fun div(dividerX: Float, dividerY: Float): Vector2f

    operator fun unaryPlus(): Vector2f
    operator fun unaryMinus(): Vector2f
    operator fun inc(): Vector2f
    operator fun dec(): Vector2f
    operator fun plus(increment: Float): Vector2f
    operator fun plus(increment: Vector2f): Vector2f
    operator fun minus(decrement: Float): Vector2f
    operator fun minus(decrement: Vector2f): Vector2f
    operator fun times(multiplier: Float): Vector2f
    operator fun div(divider: Float): Vector2f

    companion object {
        val ZERO: Vector2f = MutableVector2f(0f, 0f)
    }
}

operator fun Float.plus(vector: Vector2f): Vector2f = vector.plus(this)
operator fun Float.minus(vector: Vector2f): Vector2f = vector.minus(this)
operator fun Float.times(vector: Vector2f): Vector2f = vector.times(this)
operator fun Float.div(vector: Vector2f): Vector2f = vector.div(this)

fun vec2f(x: Float, y: Float): Vector2f = MutableVector2f(x, y)
fun vec2f(x: Number, y: Number): Vector2f = MutableVector2f(x.toFloat(), y.toFloat())