package me.elsiff.rebooted.engine.math

import kotlin.math.sqrt

/**
 * Created by elsiff on 2019-07-24.
 */
open class Vector3f(
    x: Float, y: Float, z: Float
) : Vector<Float> {
    override val elements: List<Float> = listOf(x, y, z)

    open val x: Float get() = elements[0]
    open val y: Float get() = elements[1]
    open val z: Float get() = elements[2]

    operator fun get(i: Int): Float {
        require(i in 0..2) { throw IndexOutOfBoundsException() }
        return elements[i]
    }

    override val length: Float get() = sqrt(lengthSquared)
    override val lengthSquared: Float get() = this dot this

    infix fun dot(other: Vector3f): Float = ((x * other.x) + (y * other.y) + (z * other.z))

    override fun normalized(): Vector3f {
        val len = length
        return Vector3f(x / len, y / len, z / len)
    }

    operator fun plus(increment: Vector3f) =
        Vector3f(x + increment.x, y + increment.y, z + increment.z)
    operator fun minus(decrement: Vector3f) =
        Vector3f(x - decrement.x, y - decrement.y, z - decrement.z)

    override operator fun unaryPlus() = Vector3f(x, y, z)
    override operator fun unaryMinus() = Vector3f(-x, -y, -z)

    override operator fun plus(increment: Float) =
        Vector3f(x + increment, y + increment, z + increment)
    override operator fun minus(decrement: Float) =
        Vector3f(x - decrement, y - decrement, z - decrement)

    override operator fun times(multiplier: Float) =
        Vector3f(x * multiplier, y * multiplier, z * multiplier)
    override operator fun div(divider: Float) =
        Vector3f(x / divider, y / divider, z / divider)

    fun toMutable() = MutableVector3f(x, y, z)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vector3f) return false

        if (elements != other.elements) return false

        return true
    }

    override fun hashCode(): Int {
        return elements.hashCode()
    }

    override fun toString(): String = "{$x, $y, $z}"

    companion object {
        val ZERO: Vector3f = Vector3f(0f, 0f, 0f)
    }
}

operator fun Float.plus(vector: Vector3f): Vector3f = vector.plus(this)
operator fun Float.minus(vector: Vector3f): Vector3f = vector.minus(this)
operator fun Float.times(vector: Vector3f): Vector3f = vector.times(this)
operator fun Float.div(vector: Vector3f): Vector3f = vector.div(this)

fun vec3f(x: Float, y: Float, z: Float) = Vector3f(x, y, z)
fun vec3f(x: Number, y: Number, z: Number) = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())