package me.elsiff.rebooted.engine.lwjgl.window.graphic.batch

import org.lwjgl.opengl.GL30.*

/**
 * Created by elsiff on 2019-02-28.
 */
class VertexArrayObject {
    private val id = glGenVertexArrays()

    fun bind() {
        glBindVertexArray(id)
    }

    fun delete() {
        glDeleteVertexArrays(id)
    }
}