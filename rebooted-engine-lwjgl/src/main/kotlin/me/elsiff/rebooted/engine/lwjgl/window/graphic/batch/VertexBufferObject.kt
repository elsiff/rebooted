package me.elsiff.rebooted.engine.lwjgl.window.graphic.batch

import org.lwjgl.opengl.GL15.*
import java.nio.FloatBuffer

/**
 * Created by elsiff on 2019-02-28.
 */
class VertexBufferObject {
    private val id = glGenBuffers()

    fun bind(target: Int) {
        glBindBuffer(target, id)
    }

    fun uploadData(target: Int, size: Long, usage: Int) {
        glBufferData(target, size, usage)
    }

    fun uploadSubData(target: Int, offset: Long, data: FloatBuffer) {
        glBufferSubData(target, offset, data)
    }

    fun delete() {
        glDeleteBuffers(id)
    }
}