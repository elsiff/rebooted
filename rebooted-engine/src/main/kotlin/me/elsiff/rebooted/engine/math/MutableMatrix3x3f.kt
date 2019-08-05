package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-08-06.
 */
open class MutableMatrix3x3f(
    override var elements: MutableList<Float>
) : Matrix3x3f(elements) {
    constructor(
        e00: Float, e01: Float, e02: Float,
        e10: Float, e11: Float, e12: Float,
        e20: Float, e21: Float, e22: Float
    ) : this(
        mutableListOf(
            e00, e01, e02,
            e10, e11, e12,
            e20, e21, e22
        )
    )

    operator fun set(i: Int, j: Int, value: Float) {
        require(i in 0..2 && j in 0..2) { throw IndexOutOfBoundsException() }
        elements[i * 3 + j] = value
    }

    operator fun set(j: Int, column: Vector3f) {
        for (i in 0..2) {
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

operator fun Float.plus(matrix: MutableMatrix3x3f) = matrix.plus(this)
operator fun Float.minus(matrix: MutableMatrix3x3f) = matrix.minus(this)
operator fun Float.times(matrix: MutableMatrix3x3f) = matrix.times(this)
operator fun Float.div(matrix: MutableMatrix3x3f) = matrix.div(this)

fun mutableMat3x3f(
    e00: Float, e01: Float, e02: Float,
    e10: Float, e11: Float, e12: Float,
    e20: Float, e21: Float, e22: Float
) = MutableMatrix3x3f(
    e00, e01, e02,
    e10, e11, e12,
    e20, e21, e22
)

fun mutableMat3x3f(
    e00: Number, e01: Number, e02: Number,
    e10: Number, e11: Number, e12: Number,
    e20: Number, e21: Number, e22: Number
) = MutableMatrix3x3f(
    e00.toFloat(), e01.toFloat(), e02.toFloat(),
    e10.toFloat(), e11.toFloat(), e12.toFloat(),
    e20.toFloat(), e21.toFloat(), e22.toFloat()
)

fun mutableMat3x3f(column0: Vector3f, column1: Vector3f, column2: Vector2f) = MutableMatrix3x3f(
    column0[0], column1[0], column2[0],
    column0[1], column1[1], column2[1],
    column0[2], column1[2], column2[2]
)