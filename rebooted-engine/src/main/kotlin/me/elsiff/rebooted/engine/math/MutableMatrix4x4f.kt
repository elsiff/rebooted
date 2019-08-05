package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-08-06.
 */
open class MutableMatrix4x4f(
    override var elements: MutableList<Float>
) : Matrix4x4f(elements) {
    constructor(
        e00: Float, e01: Float, e02: Float, e03: Float,
        e10: Float, e11: Float, e12: Float, e13: Float,
        e20: Float, e21: Float, e22: Float, e23: Float,
        e30: Float, e31: Float, e32: Float, e33: Float
    ) : this(
        mutableListOf(
            e00, e01, e02, e03,
            e10, e11, e12, e13,
            e20, e21, e22, e23,
            e30, e31, e32, e33
        )
    )

    operator fun set(i: Int, j: Int, value: Float) {
        require(i in 0..3 && j in 0..3) { throw IndexOutOfBoundsException() }
        elements[i * 4 + j] = value
    }

    operator fun set(j: Int, column: Vector4f) {
        for (i in 0..3) {
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

operator fun Float.plus(matrix: MutableMatrix4x4f) = matrix.plus(this)
operator fun Float.minus(matrix: MutableMatrix4x4f) = matrix.minus(this)
operator fun Float.times(matrix: MutableMatrix4x4f) = matrix.times(this)
operator fun Float.div(matrix: MutableMatrix4x4f) = matrix.div(this)

fun mutableMat4x4f(
    e00: Float, e01: Float, e02: Float, e03: Float,
    e10: Float, e11: Float, e12: Float, e13: Float,
    e20: Float, e21: Float, e22: Float, e23: Float,
    e30: Float, e31: Float, e32: Float, e33: Float
) = MutableMatrix4x4f(
    e00, e01, e02, e03,
    e10, e11, e12, e13,
    e20, e21, e22, e23,
    e30, e31, e32, e33
)

fun mutableMat4x4f(
    e00: Number, e01: Number, e02: Number, e03: Number,
    e10: Number, e11: Number, e12: Number, e13: Number,
    e20: Number, e21: Number, e22: Number, e23: Number,
    e30: Number, e31: Number, e32: Number, e33: Number
) = MutableMatrix4x4f(
    e00.toFloat(), e01.toFloat(), e02.toFloat(), e03.toFloat(),
    e10.toFloat(), e11.toFloat(), e12.toFloat(), e13.toFloat(),
    e20.toFloat(), e21.toFloat(), e22.toFloat(), e23.toFloat(),
    e30.toFloat(), e31.toFloat(), e32.toFloat(), e33.toFloat()
)

fun mutableMat4x4f(column0: Vector4f, column1: Vector4f, column2: Vector2f, column3: Vector2f) = MutableMatrix4x4f(
    column0[0], column1[0], column2[0], column3[0],
    column0[1], column1[1], column2[1], column3[1],
    column0[2], column1[2], column2[2], column3[2],
    column0[3], column1[3], column2[3], column3[3]
)