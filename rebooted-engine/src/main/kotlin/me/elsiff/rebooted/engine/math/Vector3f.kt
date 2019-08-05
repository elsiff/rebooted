package me.elsiff.rebooted.engine.math

import kotlin.math.sqrt

/**
 * Created by elsiff on 2019-07-24.
 */
interface Vector3f {
    val x: Float
    val y: Float
    val z: Float

    fun length(): Float = sqrt(lengthSquared())
    fun lengthSquared(): Float = ((x * x) + (y * y) + (z * z))

    infix fun dot(other: Vector3f): Float = ((x * other.x) + (y * other.y) + (z * other.z))

    fun normalized(): Vector3f

    fun plus(incrementX: Float, incrementY: Float, incrementZ: Float): Vector3f
    fun minus(decrementX: Float, decrementY: Float, decrementZ: Float): Vector3f
    fun times(multiplierX: Float, multiplierY: Float, multiplierZ: Float): Vector3f
    fun div(dividerX: Float, dividerY: Float, dividerZ: Float): Vector3f

    operator fun unaryPlus(): Vector3f
    operator fun unaryMinus(): Vector3f
    operator fun inc(): Vector3f
    operator fun dec(): Vector3f
    operator fun plus(increment: Float): Vector3f
    operator fun plus(increment: Vector3f): Vector3f
    operator fun minus(decrement: Float): Vector3f
    operator fun minus(decrement: Vector3f): Vector3f
    operator fun times(multiplier: Float): Vector3f
    operator fun div(divider: Float): Vector3f

    companion object {
        val ZERO: Vector3f = MutableVector3f(0f, 0f, 0f)
    }
}

operator fun Float.plus(vector: Vector3f): Vector3f = vector.plus(this)
operator fun Float.minus(vector: Vector3f): Vector3f = vector.minus(this)
operator fun Float.times(vector: Vector3f): Vector3f = vector.times(this)
operator fun Float.div(vector: Vector3f): Vector3f = vector.div(this)

fun vec3f(x: Float, y: Float, z: Float): Vector3f = MutableVector3f(x, y, z)
fun vec3f(x: Number, y: Number, z: Number): Vector3f = MutableVector3f(x.toFloat(), y.toFloat(), z.toFloat())