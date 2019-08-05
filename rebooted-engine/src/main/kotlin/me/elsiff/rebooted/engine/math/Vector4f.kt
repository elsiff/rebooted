package me.elsiff.rebooted.engine.math

import kotlin.math.sqrt

/**
 * Created by elsiff on 2019-08-06.
 */
open class Vector4f(
    x: Float, y: Float, z: Float, w: Float
) : Vector<Float> {
    override val elements: List<Float> = listOf(x, y, z, w)

    open val x: Float get() = elements[0]
    open val y: Float get() = elements[1]
    open val z: Float get() = elements[2]
    open val w: Float get() = elements[3]

    operator fun get(i: Int): Float {
        require(i in 0..3) { throw IndexOutOfBoundsException() }
        return elements[i]
    }

    override val length: Float get() = sqrt(lengthSquared)
    override val lengthSquared: Float get() = this dot this

    infix fun dot(other: Vector4f): Float = ((x * other.x) + (y * other.y) + (z * other.z) + (w * other.w))

    override fun normalized(): Vector4f {
        val len = length
        return Vector4f(x / len, y / len, z / len, w / len)
    }

    operator fun plus(increment: Vector4f) =
        Vector4f(x + increment.x, y + increment.y, z + increment.z, w + increment.w)
    operator fun minus(decrement: Vector4f) =
        Vector4f(x - decrement.x, y - decrement.y, z - decrement.z, w - decrement.w)

    override operator fun unaryPlus() = Vector4f(x, y, z, w)
    override operator fun unaryMinus() = Vector4f(-x, -y, -z, -w)

    override operator fun plus(increment: Float) =
        Vector4f(x + increment, y + increment, z + increment, w + increment)
    override operator fun minus(decrement: Float) =
        Vector4f(x - decrement, y - decrement, z - decrement, w - decrement)

    override operator fun times(multiplier: Float) =
        Vector4f(x * multiplier, y * multiplier, z * multiplier, w * multiplier)
    override operator fun div(divider: Float) =
        Vector4f(x / divider, y / divider, z / divider, w / divider)

    fun toMutable() = MutableVector4f(x, y, z, w)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vector4f) return false

        if (elements != other.elements) return false

        return true
    }

    override fun hashCode(): Int {
        return elements.hashCode()
    }

    override fun toString(): String = "{$x, $y, $z, $w}"

    companion object {
        val ZERO: Vector4f = Vector4f(0f, 0f, 0f, 0f)
    }
}

operator fun Float.plus(vector: Vector4f): Vector4f = vector.plus(this)
operator fun Float.minus(vector: Vector4f): Vector4f = vector.minus(this)
operator fun Float.times(vector: Vector4f): Vector4f = vector.times(this)
operator fun Float.div(vector: Vector4f): Vector4f = vector.div(this)

fun vec4f(x: Float, y: Float, z: Float, w: Float) = Vector4f(x, y, z, w)
fun vec4f(x: Number, y: Number, z: Number, w: Number) = Vector4f(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())