package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-07-24.
 */
class MutableVector3f(
    override var x: Float,
    override var y: Float,
    override val z: Float
) : Vector3f {
    override fun normalized(): Vector3f {
        val length = length()
        return MutableVector3f(x / length, y / length, z / length)
    }

    override fun plus(incrementX: Float, incrementY: Float, incrementZ: Float): MutableVector3f =
        MutableVector3f(x + incrementX, y + incrementY, z + incrementZ)

    override fun minus(decrementX: Float, decrementY: Float, decrementZ: Float): MutableVector3f =
        MutableVector3f(x - decrementX, y - decrementY, z - decrementZ)

    override fun times(multiplierX: Float, multiplierY: Float, multiplierZ: Float): MutableVector3f =
        MutableVector3f(x * multiplierX, y * multiplierY, z * multiplierZ)

    override fun div(dividerX: Float, dividerY: Float, dividerZ: Float): MutableVector3f =
        MutableVector3f(x / dividerX, y / dividerY, z / dividerZ)

    override operator fun unaryPlus(): MutableVector3f =
        MutableVector3f(x, y, z)

    override operator fun unaryMinus(): MutableVector3f =
        MutableVector3f(-x, -y, -z)

    override operator fun inc(): MutableVector3f =
        MutableVector3f(x + 1, y + 1, z + 1)

    override operator fun dec(): MutableVector3f =
        MutableVector3f(x - 1, y - 1, z - 1)

    override operator fun plus(increment: Float): MutableVector3f =
        MutableVector3f(x + increment, y + increment, z + increment)

    override operator fun plus(increment: Vector3f): MutableVector3f =
        MutableVector3f(x + increment.x, y + increment.y, z + increment.z)

    override operator fun minus(decrement: Float): MutableVector3f =
        MutableVector3f(x - decrement, y - decrement, z - decrement)

    override operator fun minus(decrement: Vector3f): MutableVector3f =
        MutableVector3f(x - decrement.x, y - decrement.y, z - decrement.z)

    override operator fun times(multiplier: Float): MutableVector3f =
        MutableVector3f(x * multiplier, y * multiplier, z * multiplier)

    override operator fun div(divider: Float): MutableVector3f =
        MutableVector3f(x / divider, y / divider, z / divider)

    override fun toString(): String =
        "{x: $x, y: $y, z: $z}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MutableVector3f

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }
}

operator fun Float.plus(vector: MutableVector3f): MutableVector3f = vector.plus(this)
operator fun Float.minus(vector: MutableVector3f): MutableVector3f = vector.minus(this)
operator fun Float.times(vector: MutableVector3f): MutableVector3f = vector.times(this)
operator fun Float.div(vector: MutableVector3f): MutableVector3f = vector.div(this)

fun mutableVec3f(x: Float = 0f, y: Float = 0f, z: Float = 0f) = MutableVector3f(x, y, z)
fun mutableVec3f(x: Number, y: Number, z: Number) = MutableVector3f(x.toFloat(), y.toFloat(), z.toFloat())