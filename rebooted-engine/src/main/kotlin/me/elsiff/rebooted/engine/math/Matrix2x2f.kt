package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-08-06.
 */
open class Matrix2x2f protected constructor(
    override val elements: List<Float>
) : Matrix<Float> {
    constructor(
        e00: Float, e01: Float,
        e10: Float, e11: Float
    ) : this(
        listOf(
            e00, e01,
            e10, e11
        )
    )

    operator fun get(i: Int, j: Int): Float {
        require(i in 0..1 && j in 0..1) { throw IndexOutOfBoundsException() }
        return elements[i * 2 + j]
    }

    operator fun get(j: Int) =
        vec2f(this[0, j], this[1, j])

    override fun transposed() = Matrix2x2f(
        this[0, 0], this[1, 0],
        this[0, 1], this[1, 1]
    )

    override fun unaryPlus() = Matrix2x2f(elements)
    override fun unaryMinus() = Matrix2x2f(elements.map { -it })

    override fun plus(increment: Float) = Matrix2x2f(elements.map { it + increment })
    override fun minus(decrement: Float) = Matrix2x2f(elements.map { it - decrement })

    override fun times(multiplier: Float) = Matrix2x2f(elements.map { it * multiplier })
    override fun div(divider: Float) = Matrix2x2f(elements.map { it / divider })

    fun toMutable() = MutableMatrix2x2f(elements.toMutableList())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Matrix2x2f) return false

        if (elements != other.elements) return false

        return true
    }

    override fun hashCode(): Int {
        return elements.hashCode()
    }

    override fun toString(): String = "{${this[0]}, ${this[1]}}"

    companion object {
        val ZERO: Matrix2x2f = Matrix2x2f(
            0f, 0f,
            0f, 0f
        )
        val IDENTITY: Matrix2x2f = Matrix2x2f(
            1f, 0f,
            0f, 1f
        )
    }
}

operator fun Float.plus(matrix: Matrix2x2f) = matrix.plus(this)
operator fun Float.minus(matrix: Matrix2x2f) = matrix.minus(this)
operator fun Float.times(matrix: Matrix2x2f) = matrix.times(this)
operator fun Float.div(matrix: Matrix2x2f) = matrix.div(this)

fun mat2x2f(
    e00: Float, e01: Float,
    e10: Float, e11: Float
) = Matrix2x2f(
    e00, e01,
    e10, e11
)

fun mat2x2f(
    e00: Number, e01: Number,
    e10: Number, e11: Number
) = Matrix2x2f(
    e00.toFloat(), e01.toFloat(),
    e10.toFloat(), e11.toFloat()
)

fun mat2x2f(column0: Vector2f, column1: Vector2f) = Matrix2x2f(
    column0[0], column1[0],
    column0[1], column1[1]
)