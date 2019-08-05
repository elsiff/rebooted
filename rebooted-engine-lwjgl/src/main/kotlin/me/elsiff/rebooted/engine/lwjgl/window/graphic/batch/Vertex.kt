package me.elsiff.rebooted.engine.lwjgl.window.graphic.batch

/**
 * Created by elsiff on 2019-03-04.
 */
data class Vertex(
    val values: FloatArray
) {
    fun size() = values.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vertex

        if (!values.contentEquals(other.values)) return false

        return true
    }

    override fun hashCode(): Int {
        return values.contentHashCode()
    }

    companion object {
        fun of(vararg values: Float): Vertex = Vertex(values)
    }
}