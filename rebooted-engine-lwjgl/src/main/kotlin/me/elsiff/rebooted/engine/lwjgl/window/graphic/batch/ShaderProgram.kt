package me.elsiff.rebooted.engine.lwjgl.window.graphic.batch

import me.elsiff.rebooted.engine.math.Matrix4f
import me.elsiff.rebooted.engine.window.Window
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.glBindFragDataLocation
import org.lwjgl.system.MemoryStack

/**
 * Created by elsiff on 2019-03-04.
 */
abstract class ShaderProgram(
    private val window: Window
) {
    val id = glCreateProgram()

    abstract val attributeAmount: Int

    abstract fun setUniformValues(windowWidth: Float, windowHeight: Float)

    abstract fun specifyVertexAttributes()

    fun attachShader(shader: Shader) {
        glAttachShader(id, shader.id)
    }

    fun bindFragmentDataLocation(number: Int, name: CharSequence) {
        glBindFragDataLocation(id, number, name)
    }

    fun link() {
        glLinkProgram(id)

        val status = glGetProgrami(id, GL_LINK_STATUS)
        if (status != GL_TRUE) {
            throw IllegalStateException(glGetProgramInfoLog(id))
        }
    }

    fun getAttributeLocation(name: CharSequence): Int {
        return glGetAttribLocation(id, name)
    }

    fun enableVertexAttribute(location: Int) {
        glEnableVertexAttribArray(location)
    }

    fun pointVertexAttribute(location: Int, size: Int, type: Int, stride: Int, offset: Long) {
        glVertexAttribPointer(location, size, type, false, stride, offset)
    }

    fun getUniformLocation(name: CharSequence): Int {
        return glGetUniformLocation(id, name)
    }

    fun setUniform(location: Int, value: Int) {
        glUniform1i(location, value)
    }

    fun setUniform(location: Int, matrix: Matrix4f) {
        MemoryStack.stackPush().use { stack ->
            val buffer = stack.mallocFloat(4 * 4)
            for (element in matrix.transpose()) {
                buffer.put(element)
            }
            buffer.flip()

            glUniformMatrix4fv(location, false, buffer)
        }
    }

    fun use() {
        glUseProgram(id)
    }

    fun delete() {
        glDeleteProgram(id)
    }

    fun readyForDrawing() {
        this.setUniformValues(
            window.size.width, window.size.height
        )
        this.specifyVertexAttributes()
    }
}