package me.elsiff.rebooted.engine.lwjgl.window.graphic.batch

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.lwjgl.window.GlfwWindow
import me.elsiff.rebooted.engine.window.graphic.Color
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW
import org.lwjgl.system.MemoryUtil
import java.lang.Float
import java.nio.FloatBuffer

/**
 * Created by elsiff on 2019-02-28.
 */
class Batch(
    private val window: GlfwWindow
) : Disposable {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    private val vao: VertexArrayObject = VertexArrayObject()
    private val vbo: VertexBufferObject = VertexBufferObject()
    private val programSelection: ShaderProgramSelection
    private val verticesBuffer: FloatBuffer

    private var numVertices = 0
    private var isDrawing = false

    init {
        window.makeContextCurrent()

        vao.bind()
        vbo.bind(GL_ARRAY_BUFFER)

        verticesBuffer = MemoryUtil.memAllocFloat(4096)

        val size = verticesBuffer.capacity().toLong() * Float.BYTES
        vbo.uploadData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW)
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        programSelection = ShaderProgramSelection(window)
    }

    fun clear(color: Color) {
        window.makeContextCurrent()

        glClear(GL_COLOR_BUFFER_BIT)
        glClearColor(color.red, color.green, color.blue, color.alpha)
    }

    fun begin() {
        if (isDrawing) {
            throw IllegalStateException("Batch is already drawing")
        }
        isDrawing = true
        numVertices = 0
    }

    fun end() {
        if (!isDrawing) {
            throw IllegalStateException("Batch isn't drawing")
        }
        flush()
        isDrawing = false
    }

    fun flush() {
        if (numVertices > 0) {
            window.makeContextCurrent()
            programSelection.current.readyForDrawing()
            verticesBuffer.flip()

            vao.bind()

            vbo.bind(GL_ARRAY_BUFFER)
            vbo.uploadSubData(GL_ARRAY_BUFFER, 0, verticesBuffer)

            glDrawArrays(GL_TRIANGLES, 0, numVertices)

            verticesBuffer.clear()
            numVertices = 0
        }
    }

    fun putVertices(programType: ShaderProgramType, vararg addendVertices: Vertex) {
        if (!isDrawing) {
            throw IllegalStateException("Batch isn't drawing")
        }

        if (programSelection.currentType != programType) {
            flush()
            programSelection.select(programType)
        } else {
            val shaderProgram = programSelection.current
            if (this.verticesBuffer.remaining() < addendVertices.size * shaderProgram.attributeAmount) {
                flush()
            }
        }

        for (vertex in addendVertices) {
            verticesBuffer.put(vertex.values)
        }

        numVertices += addendVertices.size
    }

    override fun dispose() {
        MemoryUtil.memFree(verticesBuffer)

        vao.delete()
        vbo.delete()

        programSelection.dispose()
    }
}