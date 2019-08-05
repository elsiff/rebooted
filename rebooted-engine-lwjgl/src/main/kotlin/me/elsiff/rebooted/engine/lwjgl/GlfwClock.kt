package me.elsiff.rebooted.engine.lwjgl

import me.elsiff.rebooted.engine.Clock
import org.lwjgl.glfw.GLFW.glfwGetTime

/**
 * Created by elsiff on 2019-02-27.
 */
class GlfwClock : Clock {
    override val currentTime: Double
        get() = glfwGetTime()
    override val delta: Float
        get() = _delta

    private var lastTime: Double = currentTime
    private var _delta: Float = 0f

    override fun update() {
        _delta = (currentTime - lastTime).toFloat()
        lastTime = currentTime
    }
}