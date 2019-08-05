package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-08-06.
 */
class MutableVector4f(
    x: Float, y: Float, z: Float, w: Float
) : Vector4f(x, y, z, w) {
    override var elements: MutableList<Float> = mutableListOf(x, y, z, w)

    override var x: Float
        get() = elements[0]
        set(value) {
            elements[0] = value
        }
    override var y: Float
        get() = elements[1]
        set(value) {
            elements[1] = value
        }
    override var z: Float
        get() = elements[2]
        set(value) {
            elements[2] = value
        }
    override var w: Float
        get() = elements[3]
        set(value) {
            elements[3] = value
        }

    operator fun set(i: Int, value: Float) {
        require(i in 0..3) { throw IndexOutOfBoundsException() }
        elements[i] = value
    }

    override fun normalized() = super.normalized().toMutable()
    override fun unaryPlus() = super.unaryPlus().toMutable()
    override fun unaryMinus() = super.unaryMinus().toMutable()
    override fun plus(increment: Float) = super.plus(increment).toMutable()
    override fun minus(decrement: Float) = super.minus(decrement).toMutable()
    override fun times(multiplier: Float) = super.times(multiplier).toMutable()
    override fun div(divider: Float) = super.div(divider).toMutable()
}

operator fun Float.plus(vector: MutableVector4f): MutableVector4f = vector.plus(this)
operator fun Float.minus(vector: MutableVector4f): MutableVector4f = vector.minus(this)
operator fun Float.times(vector: MutableVector4f): MutableVector4f = vector.times(this)
operator fun Float.div(vector: MutableVector4f): MutableVector4f = vector.div(this)

fun mutableVec4f(x: Float, y: Float, z: Float, w: Float) = MutableVector4f(x, y, z, w)
fun mutableVec4f(x: Number, y: Number, z: Number, w: Number) =
    MutableVector4f(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())