package me.elsiff.rebooted.engine.lwjgl.window.input

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.event.input.*
import me.elsiff.rebooted.engine.lwjgl.window.GlfwWindow
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.math.vec2f
import me.elsiff.rebooted.engine.window.input.Input
import me.elsiff.rebooted.engine.window.input.Key
import me.elsiff.rebooted.engine.window.input.MouseButton
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*

/**
 * Created by elsiff on 2019-07-23.
 */
class GlfwInput(
    override val window: GlfwWindow
) : Input, Disposable {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    private val keyCodes: Map<Int, Key> = mapOf(
        GLFW_KEY_UNKNOWN to Key.UNKNOWN,
        GLFW_KEY_SPACE to Key.SPACE,
        GLFW_KEY_APOSTROPHE to Key.APOSTROPHE,
        GLFW_KEY_COMMA to Key.COMMA,
        GLFW_KEY_MINUS to Key.MINUS,
        GLFW_KEY_PERIOD to Key.PERIOD,
        GLFW_KEY_SLASH to Key.SLASH,
        GLFW_KEY_0 to Key.NUM_0,
        GLFW_KEY_1 to Key.NUM_1,
        GLFW_KEY_2 to Key.NUM_2,
        GLFW_KEY_3 to Key.NUM_3,
        GLFW_KEY_4 to Key.NUM_4,
        GLFW_KEY_5 to Key.NUM_5,
        GLFW_KEY_6 to Key.NUM_6,
        GLFW_KEY_7 to Key.NUM_7,
        GLFW_KEY_8 to Key.NUM_8,
        GLFW_KEY_9 to Key.NUM_9,
        GLFW_KEY_SEMICOLON to Key.SEMICOLON,
        GLFW_KEY_EQUAL to Key.EQUAL,
        GLFW_KEY_A to Key.A,
        GLFW_KEY_B to Key.B,
        GLFW_KEY_C to Key.C,
        GLFW_KEY_D to Key.D,
        GLFW_KEY_E to Key.E,
        GLFW_KEY_F to Key.F,
        GLFW_KEY_G to Key.G,
        GLFW_KEY_H to Key.H,
        GLFW_KEY_I to Key.I,
        GLFW_KEY_J to Key.J,
        GLFW_KEY_K to Key.K,
        GLFW_KEY_L to Key.L,
        GLFW_KEY_M to Key.M,
        GLFW_KEY_N to Key.N,
        GLFW_KEY_O to Key.O,
        GLFW_KEY_P to Key.P,
        GLFW_KEY_Q to Key.Q,
        GLFW_KEY_R to Key.R,
        GLFW_KEY_S to Key.S,
        GLFW_KEY_T to Key.T,
        GLFW_KEY_U to Key.U,
        GLFW_KEY_V to Key.V,
        GLFW_KEY_W to Key.W,
        GLFW_KEY_X to Key.X,
        GLFW_KEY_Y to Key.Y,
        GLFW_KEY_Z to Key.Z,
        GLFW_KEY_LEFT_BRACKET to Key.LEFT_BRACKET,
        GLFW_KEY_BACKSLASH to Key.BACKSLASH,
        GLFW_KEY_RIGHT_BRACKET to Key.RIGHT_BRACKET,
        GLFW_KEY_GRAVE_ACCENT to Key.GRAVE_ACCENT,
        GLFW_KEY_WORLD_1 to Key.WORLD_1,
        GLFW_KEY_WORLD_2 to Key.WORLD_2,
        GLFW_KEY_ESCAPE to Key.ESCAPE,
        GLFW_KEY_ENTER to Key.ENTER,
        GLFW_KEY_TAB to Key.TAB,
        GLFW_KEY_BACKSPACE to Key.BACKSPACE,
        GLFW_KEY_INSERT to Key.INSERT,
        GLFW_KEY_DELETE to Key.DELETE,
        GLFW_KEY_RIGHT to Key.RIGHT,
        GLFW_KEY_LEFT to Key.LEFT,
        GLFW_KEY_DOWN to Key.DOWN,
        GLFW_KEY_UP to Key.UP,
        GLFW_KEY_PAGE_UP to Key.PAGE_UP,
        GLFW_KEY_PAGE_DOWN to Key.PAGE_DOWN,
        GLFW_KEY_HOME to Key.HOME,
        GLFW_KEY_END to Key.END,
        GLFW_KEY_CAPS_LOCK to Key.CAPS_LOCK,
        GLFW_KEY_SCROLL_LOCK to Key.SCROLL_LOCK,
        GLFW_KEY_NUM_LOCK to Key.NUM_LOCK,
        GLFW_KEY_PRINT_SCREEN to Key.PRINT_SCREEN,
        GLFW_KEY_PAUSE to Key.PAUSE,
        GLFW_KEY_F1 to Key.F1,
        GLFW_KEY_F2 to Key.F2,
        GLFW_KEY_F3 to Key.F3,
        GLFW_KEY_F4 to Key.F4,
        GLFW_KEY_F5 to Key.F5,
        GLFW_KEY_F6 to Key.F6,
        GLFW_KEY_F7 to Key.F7,
        GLFW_KEY_F8 to Key.F8,
        GLFW_KEY_F9 to Key.F9,
        GLFW_KEY_F10 to Key.F10,
        GLFW_KEY_F11 to Key.F11,
        GLFW_KEY_F12 to Key.F12,
        GLFW_KEY_F13 to Key.F13,
        GLFW_KEY_F14 to Key.F14,
        GLFW_KEY_F15 to Key.F15,
        GLFW_KEY_F16 to Key.F16,
        GLFW_KEY_F17 to Key.F17,
        GLFW_KEY_F18 to Key.F18,
        GLFW_KEY_F19 to Key.F19,
        GLFW_KEY_F20 to Key.F20,
        GLFW_KEY_F21 to Key.F21,
        GLFW_KEY_F22 to Key.F22,
        GLFW_KEY_F23 to Key.F23,
        GLFW_KEY_F24 to Key.F24,
        GLFW_KEY_F25 to Key.F25,
        GLFW_KEY_KP_0 to Key.KP_0,
        GLFW_KEY_KP_1 to Key.KP_1,
        GLFW_KEY_KP_2 to Key.KP_2,
        GLFW_KEY_KP_3 to Key.KP_3,
        GLFW_KEY_KP_4 to Key.KP_4,
        GLFW_KEY_KP_5 to Key.KP_5,
        GLFW_KEY_KP_6 to Key.KP_6,
        GLFW_KEY_KP_7 to Key.KP_7,
        GLFW_KEY_KP_8 to Key.KP_8,
        GLFW_KEY_KP_9 to Key.KP_9,
        GLFW_KEY_KP_DECIMAL to Key.KP_DECIMAL,
        GLFW_KEY_KP_DIVIDE to Key.KP_DIVIDE,
        GLFW_KEY_KP_MULTIPLY to Key.KP_MULTIPLY,
        GLFW_KEY_KP_SUBTRACT to Key.KP_SUBTRACT,
        GLFW_KEY_KP_ADD to Key.KP_ADD,
        GLFW_KEY_KP_ENTER to Key.KP_ENTER,
        GLFW_KEY_KP_EQUAL to Key.KP_EQUAL,
        GLFW_KEY_LEFT_SHIFT to Key.LEFT_SHIFT,
        GLFW_KEY_LEFT_CONTROL to Key.LEFT_CONTROL,
        GLFW_KEY_LEFT_ALT to Key.LEFT_ALT,
        GLFW_KEY_LEFT_SUPER to Key.LEFT_SUPER,
        GLFW_KEY_RIGHT_SHIFT to Key.RIGHT_SHIFT,
        GLFW_KEY_RIGHT_CONTROL to Key.RIGHT_CONTROL,
        GLFW_KEY_RIGHT_ALT to Key.RIGHT_ALT,
        GLFW_KEY_RIGHT_SUPER to Key.RIGHT_SUPER,
        GLFW_KEY_MENU to Key.MENU,
        GLFW_KEY_LAST to Key.LAST
    )
    private val mouseCodes: Map<Int, MouseButton> = mapOf(
        GLFW_MOUSE_BUTTON_LEFT to MouseButton.LEFT,
        GLFW_MOUSE_BUTTON_MIDDLE to MouseButton.MIDDLE,
        GLFW_MOUSE_BUTTON_RIGHT to MouseButton.RIGHT
    )

    init {

        GLFWKeyCallback.create { _, keyCode, _, action, _ ->
            val event = when (action) {
                GLFW_RELEASE -> KeyReleaseEvent(this@GlfwInput, keyOf(keyCode))
                GLFW_PRESS -> KeyPressEvent(this@GlfwInput, keyOf(keyCode))
                GLFW_REPEAT -> KeyRepeatEvent(this@GlfwInput, keyOf(keyCode))
                else -> error("Invalid key action")
            }
            window.engine.eventTrigger.call(event)
        }.also { glfwSetKeyCallback(window.id, it) }

        GLFWMouseButtonCallback.create { _, buttonCode, action, _ ->
            val event = when (action) {
                GLFW_RELEASE -> MouseReleaseEvent(this@GlfwInput, mouseButtonOf(buttonCode))
                GLFW_PRESS -> MouseClickEvent(this@GlfwInput, mouseButtonOf(buttonCode))
                else -> error("Invalid key action")
            }
            window.engine.eventTrigger.call(event)
        }.also { glfwSetMouseButtonCallback(window.id, it) }

        GLFWCursorPosCallback.create { _, xPos, yPos ->
            val newPosition = vec2f(xPos, window.size.height - yPos)
            val event = MouseMoveEvent(this@GlfwInput, newPosition)
            window.engine.eventTrigger.call(event)
        }.also { glfwSetCursorPosCallback(window.id, it) }

        GLFWScrollCallback.create { _, xOffset, yOffset ->
            val event = MouseScrollEvent(this@GlfwInput, vec2f(xOffset, yOffset))
            window.engine.eventTrigger.call(event)
        }.also { glfwSetScrollCallback(window.id, it) }

        GLFWCursorEnterCallback.create { _, entered ->
            val event = when (entered) {
                true -> MouseEnterWindowEvent(this@GlfwInput)
                false -> MouseLeaveWindowEvent(this@GlfwInput)
            }
            window.engine.eventTrigger.call(event)
        }.also { glfwSetCursorEnterCallback(window.id, it) }
    }

    override fun isPressed(key: Key): Boolean {
        val state = glfwGetKey(window.id, key.code)
        return isPressingState(state)
    }

    override fun isClicked(mouseButton: MouseButton): Boolean {
        val state = glfwGetMouseButton(
            window.id,
            mouseButton.code
        )
        return isPressingState(state)
    }

    override fun mousePosition(): Vector2f {
        val buffer1 = BufferUtils.createDoubleBuffer(1)
        val buffer2 = BufferUtils.createDoubleBuffer(1)
        glfwGetCursorPos(window.id, buffer1, buffer2)

        val x = buffer1.get(0).toFloat()
        val y = buffer2.get(0).toFloat()
        return vec2f(x, window.size.height - y)
    }

    private fun isPressingState(state: Int): Boolean {
        return when (state) {
            GLFW_PRESS -> true
            GLFW_RELEASE -> false
            else -> error("Invalid value returned from a GLFW method")
        }
    }

    private fun keyOf(keyCode: Int) =
        keyCodes[keyCode] ?: throw IllegalArgumentException("Invalid key code")

    private val Key.code: Int
        get() = keyCodes.entries.find { it.value == this }?.key ?: error("Invalid key")

    private fun mouseButtonOf(mouseCode: Int) =
        mouseCodes[mouseCode] ?: throw IllegalArgumentException("Invalid mouse button code")

    private val MouseButton.code: Int
        get() = mouseCodes.entries.find { it.value == this }?.key ?: error("Invalid mouse button")

    override fun dispose() {
        check(!_isDisposed)
        Callbacks.glfwFreeCallbacks(window.id)
        _isDisposed = true
    }
}