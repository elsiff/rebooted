package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-08-06.
 */
open class Matrix4x4f protected constructor(
    override val elements: List<Float>
) : Matrix<Float> {
    constructor(
        e00: Float, e01: Float, e02: Float, e03: Float,
        e10: Float, e11: Float, e12: Float, e13: Float,
        e20: Float, e21: Float, e22: Float, e23: Float,
        e30: Float, e31: Float, e32: Float, e33: Float
    ) : this(
        listOf(
            e00, e01, e02, e03,
            e10, e11, e12, e13,
            e20, e21, e22, e23,
            e30, e31, e32, e33
        )
    )

    operator fun get(i: Int, j: Int): Float {
        require(i in 0..3 && j in 0..3) { throw IndexOutOfBoundsException() }
        return elements[i * 4 + j]
    }

    operator fun get(j: Int) =
        vec4f(this[0, j], this[1, j], this[2, j], this[3, j])

    override fun transposed() = Matrix4x4f(
        this[0, 0], this[1, 0], this[2, 0], this[3, 0],
        this[0, 1], this[1, 1], this[2, 1], this[3, 1],
        this[0, 2], this[1, 2], this[2, 2], this[3, 2],
        this[0, 3], this[1, 3], this[2, 3], this[3, 3]
    )

    override fun unaryPlus() = Matrix4x4f(elements)
    override fun unaryMinus() = Matrix4x4f(elements.map { -it })

    override fun plus(increment: Float) = Matrix4x4f(elements.map { it + increment })
    override fun minus(decrement: Float) = Matrix4x4f(elements.map { it - decrement })

    override fun times(multiplier: Float) = Matrix4x4f(elements.map { it * multiplier })
    override fun div(divider: Float) = Matrix4x4f(elements.map { it / divider })

    fun toMutable() = MutableMatrix4x4f(elements.toMutableList())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Matrix4x4f) return false

        if (elements != other.elements) return false

        return true
    }

    override fun hashCode(): Int {
        return elements.hashCode()
    }

    override fun toString(): String = "{${this[0]}, ${this[1]}, ${this[2]}, ${this[3]}}"

    companion object {
        val ZERO: Matrix4x4f = Matrix4x4f(
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f
        )
        val IDENTITY: Matrix4x4f = Matrix4x4f(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
        )

        fun orthographic(
            left: Float, right: Float,
            bottom: Float, top: Float,
            near: Float, far: Float
        ): Matrix4x4f {
            val tx = -(right + left) / (right - left)
            val ty = -(top + bottom) / (top - bottom)
            val tz = -(far + near) / (far - near)

            return Matrix4x4f(
                2f / (right - left), 0f, 0f, tx,
                0f, 2f / (top - bottom), 0f, ty,
                0f, 0f, -2f / (far - near), tz,
                0f, 0f, 0f, 1f
            )
        }
    }
}

operator fun Float.plus(matrix: Matrix4x4f) = matrix.plus(this)
operator fun Float.minus(matrix: Matrix4x4f) = matrix.minus(this)
operator fun Float.times(matrix: Matrix4x4f) = matrix.times(this)
operator fun Float.div(matrix: Matrix4x4f) = matrix.div(this)

fun mat4x4f(
    e00: Float, e01: Float, e02: Float, e03: Float,
    e10: Float, e11: Float, e12: Float, e13: Float,
    e20: Float, e21: Float, e22: Float, e23: Float,
    e30: Float, e31: Float, e32: Float, e33: Float
) = Matrix4x4f(
    e00, e01, e02, e03,
    e10, e11, e12, e13,
    e20, e21, e22, e23,
    e30, e31, e32, e33
)

fun mat4x4f(
    e00: Number, e01: Number, e02: Number, e03: Number,
    e10: Number, e11: Number, e12: Number, e13: Number,
    e20: Number, e21: Number, e22: Number, e23: Number,
    e30: Number, e31: Number, e32: Number, e33: Number
) = Matrix4x4f(
    e00.toFloat(), e01.toFloat(), e02.toFloat(), e03.toFloat(),
    e10.toFloat(), e11.toFloat(), e12.toFloat(), e13.toFloat(),
    e20.toFloat(), e21.toFloat(), e22.toFloat(), e23.toFloat(),
    e30.toFloat(), e31.toFloat(), e32.toFloat(), e33.toFloat()
)

fun mat4x4f(column0: Vector4f, column1: Vector4f, column2: Vector2f, column3: Vector2f) = Matrix4x4f(
    column0[0], column1[0], column2[0], column3[0],
    column0[1], column1[1], column2[1], column3[1],
    column0[2], column1[2], column2[2], column3[2],
    column0[3], column1[3], column2[3], column3[3]
)