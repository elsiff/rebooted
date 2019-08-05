package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-08-06.
 */
open class Matrix3x3f protected constructor(
    override val elements: List<Float>
) : Matrix<Float> {
    constructor(
        e00: Float, e01: Float, e02: Float,
        e10: Float, e11: Float, e12: Float,
        e20: Float, e21: Float, e22: Float
    ) : this(
        listOf(
            e00, e01, e02,
            e10, e11, e12,
            e20, e21, e22
        )
    )

    operator fun get(i: Int, j: Int): Float {
        require(i in 0..2 && j in 0..2) { throw IndexOutOfBoundsException() }
        return elements[i * 3 + j]
    }

    operator fun get(j: Int) =
        vec3f(this[0, j], this[1, j], this[2, j])

    override fun transposed() = Matrix3x3f(
        this[0, 0], this[1, 0], this[2, 0],
        this[0, 1], this[1, 1], this[2, 1],
        this[0, 2], this[1, 2], this[2, 2]
    )

    override fun unaryPlus() = Matrix3x3f(elements)
    override fun unaryMinus() = Matrix3x3f(elements.map { -it })

    override fun plus(increment: Float) = Matrix3x3f(elements.map { it + increment })
    override fun minus(decrement: Float) = Matrix3x3f(elements.map { it - decrement })

    override fun times(multiplier: Float) = Matrix3x3f(elements.map { it * multiplier })
    override fun div(divider: Float) = Matrix3x3f(elements.map { it / divider })

    fun toMutable() = MutableMatrix3x3f(elements.toMutableList())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Matrix3x3f) return false

        if (elements != other.elements) return false

        return true
    }

    override fun hashCode(): Int {
        return elements.hashCode()
    }

    override fun toString(): String = "{${this[0]}, ${this[1]}, ${this[2]}}"

    companion object {
        val ZERO: Matrix3x3f = Matrix3x3f(
            0f, 0f, 0f,
            0f, 0f, 0f,
            0f, 0f, 0f
        )
        val IDENTITY: Matrix3x3f = Matrix3x3f(
            1f, 0f, 0f,
            0f, 1f, 0f,
            0f, 0f, 1f
        )
    }
}

operator fun Float.plus(matrix: Matrix3x3f) = matrix.plus(this)
operator fun Float.minus(matrix: Matrix3x3f) = matrix.minus(this)
operator fun Float.times(matrix: Matrix3x3f) = matrix.times(this)
operator fun Float.div(matrix: Matrix3x3f) = matrix.div(this)

fun mat3x3f(
    e00: Float, e01: Float, e02: Float,
    e10: Float, e11: Float, e12: Float,
    e20: Float, e21: Float, e22: Float
) = Matrix3x3f(
    e00, e01, e02,
    e10, e11, e12,
    e20, e21, e22
)

fun mat3x3f(
    e00: Number, e01: Number, e02: Number,
    e10: Number, e11: Number, e12: Number,
    e20: Number, e21: Number, e22: Number
) = Matrix3x3f(
    e00.toFloat(), e01.toFloat(), e02.toFloat(),
    e10.toFloat(), e11.toFloat(), e12.toFloat(),
    e20.toFloat(), e21.toFloat(), e22.toFloat()
)

fun mat3x3f(column0: Vector3f, column1: Vector3f, column2: Vector2f) = Matrix3x3f(
    column0[0], column1[0], column2[0],
    column0[1], column1[1], column2[1],
    column0[2], column1[2], column2[2]
)