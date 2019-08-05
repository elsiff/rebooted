package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-08-06.
 */
open class MutableMatrix2x2f(
    override var elements: MutableList<Float>
) : Matrix2x2f(elements) {
    constructor(
        e00: Float, e01: Float,
        e10: Float, e11: Float
    ) : this(
        mutableListOf(
            e00, e01,
            e10, e11
        )
    )

    operator fun set(i: Int, j: Int, value: Float) {
        require(i in 0..1 && j in 0..1) { throw IndexOutOfBoundsException() }
        elements[i * 2 + j] = value
    }

    operator fun set(j: Int, column: Vector2f) {
        for (i in 0..1) {
            this[i, j] = column[i]
        }
    }

    override fun transposed() = super.transposed().toMutable()
    override fun unaryPlus() = super.unaryPlus().toMutable()
    override fun unaryMinus() = super.unaryMinus().toMutable()
    override fun plus(increment: Float) = super.plus(increment).toMutable()
    override fun minus(decrement: Float) = super.minus(decrement).toMutable()
    override fun times(multiplier: Float) = super.times(multiplier).toMutable()
    override fun div(divider: Float) = super.div(divider).toMutable()
}

operator fun Float.plus(matrix: MutableMatrix2x2f) = matrix.plus(this)
operator fun Float.minus(matrix: MutableMatrix2x2f) = matrix.minus(this)
operator fun Float.times(matrix: MutableMatrix2x2f) = matrix.times(this)
operator fun Float.div(matrix: MutableMatrix2x2f) = matrix.div(this)

fun mutableMat2x2f(
    e00: Float, e01: Float,
    e10: Float, e11: Float
) = MutableMatrix2x2f(
    e00, e01,
    e10, e11
)

fun mutableMat2x2f(
    e00: Number, e01: Number,
    e10: Number, e11: Number
) = MutableMatrix2x2f(
    e00.toFloat(), e01.toFloat(),
    e10.toFloat(), e11.toFloat()
)

fun mutableMat2x2f(column0: Vector2f, column1: Vector2f) = MutableMatrix2x2f(
    column0[0], column1[0],
    column0[1], column1[1]
)