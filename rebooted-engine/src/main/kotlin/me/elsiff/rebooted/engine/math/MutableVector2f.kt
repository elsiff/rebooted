package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-07-08.
 */
class MutableVector2f(
    x: Float, y: Float
) : Vector2f(x, y) {
    override var elements: MutableList<Float> = mutableListOf(x, y)

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

    operator fun set(i: Int, value: Float) {
        require(i in 0..1) { throw IndexOutOfBoundsException() }
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

operator fun Float.plus(vector: MutableVector2f): MutableVector2f = vector.plus(this)
operator fun Float.minus(vector: MutableVector2f): MutableVector2f = vector.minus(this)
operator fun Float.times(vector: MutableVector2f): MutableVector2f = vector.times(this)
operator fun Float.div(vector: MutableVector2f): MutableVector2f = vector.div(this)

fun mutableVec2f(x: Float, y: Float) = MutableVector2f(x, y)
fun mutableVec2f(x: Number, y: Number) = MutableVector2f(x.toFloat(), y.toFloat())