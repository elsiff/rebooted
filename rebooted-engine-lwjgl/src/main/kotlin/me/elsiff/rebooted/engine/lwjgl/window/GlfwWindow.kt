package me.elsiff.rebooted.engine.lwjgl.window

import me.elsiff.rebooted.engine.Engine
import me.elsiff.rebooted.engine.lwjgl.GlfwException
import me.elsiff.rebooted.engine.lwjgl.window.graphic.GlfwGraphics
import me.elsiff.rebooted.engine.lwjgl.window.input.GlfwInput
import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.window.Window
import me.elsiff.rebooted.engine.window.graphic.Graphics
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryUtil.NULL

class GlfwWindow(
    override val engine: Engine,
    private var _title: String,
    private var _size: Size
) : Window {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    val id: Long

    init {
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)

        id = glfwCreateWindow(
            _size.width.toInt(),
            _size.height.toInt(),
            _title,
            NULL,
            NULL
        )

        if (id == NULL) {
            glfwTerminate()
            throw GlfwException("Failed to create the GLFW window")
        }

        val videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!
        glfwSetWindowPos(
            id,
            (videoMode.width() - _size.width.toInt()) / 2,
            (videoMode.height() - _size.height.toInt()) / 2
        )

        glfwMakeContextCurrent(id)
        GL.createCapabilities()

        glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_HIDDEN)
    }

    override var title: String
        get() = _title
        set(value) {
            _title = value
            glfwSetWindowTitle(id, _title)
        }

    override var size: Size
        get() = _size
        set(value) {
            resize(value.width.toInt(), value.height.toInt())
        }

    override var shouldClose: Boolean
        get() = glfwWindowShouldClose(id)
        set(value) {
            glfwSetWindowShouldClose(id, value)
        }

    override val graphics: Graphics = GlfwGraphics(this)
    override val input: GlfwInput = GlfwInput(this)

    override fun update() {
        glfwSwapBuffers(id)
        glfwPollEvents()
    }

    override fun resize(width: Int, height: Int) {
        glfwSetWindowSize(id, width, height)
    }

    override fun makeContextCurrent() {
        glfwMakeContextCurrent(id)
    }

    override fun dispose() {
        check(!_isDisposed)

        input.dispose()
        glfwDestroyWindow(id)

        _isDisposed = true
    }
}