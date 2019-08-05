package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-07-08.
 */
class MutableVector2f(
    override var x: Float,
    override var y: Float
) : Vector2f {
    override fun normalized(): Vector2f {
        val length = length()
        return MutableVector2f(x / length, y / length)
    }

    override fun plus(incrementX: Float, incrementY: Float): MutableVector2f =
        MutableVector2f(x + incrementX, y + incrementY)

    override fun minus(decrementX: Float, decrementY: Float): MutableVector2f =
        MutableVector2f(x - decrementX, y - decrementY)

    override fun times(multiplierX: Float, multiplierY: Float): MutableVector2f =
        MutableVector2f(x * multiplierX, y * multiplierY)

    override fun div(dividerX: Float, dividerY: Float): MutableVector2f =
        MutableVector2f(x / dividerX, y / dividerY)

    override operator fun unaryPlus(): MutableVector2f =
        MutableVector2f(x, y)

    override operator fun unaryMinus(): MutableVector2f =
        MutableVector2f(-x, -y)

    override operator fun inc(): MutableVector2f =
        MutableVector2f(x + 1, y + 1)

    override operator fun dec(): MutableVector2f =
        MutableVector2f(x - 1, y - 1)

    override operator fun plus(increment: Float): MutableVector2f =
        MutableVector2f(x + increment, y + increment)

    override operator fun plus(increment: Vector2f): MutableVector2f =
        MutableVector2f(x + increment.x, y + increment.y)

    override operator fun minus(decrement: Float): MutableVector2f =
        MutableVector2f(x - decrement, y - decrement)

    override operator fun minus(decrement: Vector2f): MutableVector2f =
        MutableVector2f(x - decrement.x, y - decrement.y)

    override operator fun times(multiplier: Float): MutableVector2f =
        MutableVector2f(x * multiplier, y * multiplier)

    override operator fun div(divider: Float): MutableVector2f =
        MutableVector2f(x / divider, y / divider)

    override fun toString(): String =
        "{x: $x, y: $y}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MutableVector2f

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}

operator fun Float.plus(vector: MutableVector2f): MutableVector2f = vector.plus(this)
operator fun Float.minus(vector: MutableVector2f): MutableVector2f = vector.minus(this)
operator fun Float.times(vector: MutableVector2f): MutableVector2f = vector.times(this)
operator fun Float.div(vector: MutableVector2f): MutableVector2f = vector.div(this)

fun mutableVec2f(x: Float = 0f, y: Float = 0f) = MutableVector2f(x, y)
fun mutableVec2f(x: Number, y: Number) = MutableVector2f(x.toFloat(), y.toFloat())