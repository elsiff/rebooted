package me.elsiff.rebooted.engine.math

import kotlin.math.sqrt

/**
 * Created by elsiff on 2019-02-28.
 */
open class Vector2f(
    x: Float, y: Float
) : Vector<Float> {
    override val elements: List<Float> = listOf(x, y)

    open val x: Float get() = elements[0]
    open val y: Float get() = elements[1]

    operator fun get(i: Int): Float {
        require(i in 0..1) { throw IndexOutOfBoundsException() }
        return elements[i]
    }

    override val length: Float get() = sqrt(lengthSquared)
    override val lengthSquared: Float get() = this dot this

    infix fun dot(other: Vector2f): Float = ((x * other.x) + (y * other.y))

    override fun normalized(): Vector2f {
        val len = length
        return Vector2f(x / len, y / len)
    }

    operator fun plus(increment: Vector2f) = Vector2f(x + increment.x, y + increment.y)
    operator fun minus(decrement: Vector2f) = Vector2f(x - decrement.x, y - decrement.y)

    override operator fun unaryPlus() = Vector2f(x, y)
    override operator fun unaryMinus() = Vector2f(-x, -y)

    override operator fun plus(increment: Float) = Vector2f(x + increment, y + increment)
    override operator fun minus(decrement: Float) = Vector2f(x - decrement, y - decrement)

    override operator fun times(multiplier: Float) = Vector2f(x * multiplier, y * multiplier)
    override operator fun div(divider: Float) = Vector2f(x / divider, y / divider)

    fun toMutable() = MutableVector2f(x, y)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vector2f) return false

        if (elements != other.elements) return false

        return true
    }

    override fun hashCode(): Int {
        return elements.hashCode()
    }

    override fun toString(): String = "{$x, $y}"

    companion object {
        val ZERO: Vector2f = Vector2f(0f, 0f)
    }
}

operator fun Float.plus(vector: Vector2f) = vector.plus(this)
operator fun Float.minus(vector: Vector2f) = vector.minus(this)
operator fun Float.times(vector: Vector2f) = vector.times(this)
operator fun Float.div(vector: Vector2f) = vector.div(this)

fun vec2f(x: Float, y: Float) = Vector2f(x, y)
fun vec2f(x: Number, y: Number) = Vector2f(x.toFloat(), y.toFloat())