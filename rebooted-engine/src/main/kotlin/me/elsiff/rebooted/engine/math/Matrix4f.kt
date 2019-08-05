package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-03-04.
 */
data class Matrix4f(
    val matrix: Array<Array<Float>>
) : Iterable<Float> {
    constructor() : this(
        arrayOf(
            arrayOf(1f, 0f, 0f, 0f),
            arrayOf(0f, 1f, 0f, 0f),
            arrayOf(0f, 0f, 1f, 0f),
            arrayOf(0f, 0f, 0f, 1f)
        )
    )

    fun transpose(): Matrix4f =
        Matrix4f(
            arrayOf(
                arrayOf(this[0, 0], this[1, 0], this[2, 0], this[3, 0]),
                arrayOf(this[0, 1], this[1, 1], this[2, 1], this[3, 1]),
                arrayOf(this[0, 2], this[1, 2], this[2, 2], this[3, 2]),
                arrayOf(this[0, 3], this[1, 3], this[2, 3], this[3, 3])
            )
        )

    override fun iterator(): Iterator<Float> = matrix.flatten().iterator()

    operator fun get(i: Int, j: Int): Float = matrix[i][j]

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix4f

        if (!matrix.contentDeepEquals(other.matrix)) return false

        return true
    }

    override fun hashCode(): Int {
        return matrix.contentDeepHashCode()
    }

    companion object {
        fun orthographic(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix4f {
            val tx = -(right + left) / (right - left)
            val ty = -(top + bottom) / (top - bottom)
            val tz = -(far + near) / (far - near)

            return Matrix4f(
                arrayOf(
                    arrayOf(2f / (right - left), 0f, 0f, tx),
                    arrayOf(0f, 2f / (top - bottom), 0f, ty),
                    arrayOf(0f, 0f, -2f / (far - near), tz),
                    arrayOf(0f, 0f, 0f, 1f)
                )
            )
        }
    }
}