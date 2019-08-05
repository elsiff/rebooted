package me.elsiff.rebooted.engine.lwjgl

import me.elsiff.rebooted.engine.Disposable
import org.lwjgl.glfw.GLFW.glfwSetErrorCallback
import org.lwjgl.glfw.GLFWErrorCallback

/**
 * Created by elsiff on 2019-02-26.
 */
class GlfwErrorHandler : Disposable {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    private val callback: GLFWErrorCallback = GLFWErrorCallback.createThrow()
        .also {
            glfwSetErrorCallback(it)
        }

    override fun dispose() {
        check(!_isDisposed)
        callback.free()
        _isDisposed = true
    }
}