package me.elsiff.rebooted.engine.lwjgl.window.graphic.batch

import org.lwjgl.opengl.GL20.*

/**
 * Created by elsiff on 2019-03-04.
 */
class Shader(type: Int) {
    val id = glCreateShader(type)

    fun source(source: String) {
        glShaderSource(id, source)
    }

    fun compile() {
        glCompileShader(id)

        val status = glGetShaderi(id, GL_COMPILE_STATUS)
        if (status != GL_TRUE) {
            throw IllegalStateException(glGetShaderInfoLog(id))
        }
    }

    fun delete() {
        glDeleteShader(id)
    }
}