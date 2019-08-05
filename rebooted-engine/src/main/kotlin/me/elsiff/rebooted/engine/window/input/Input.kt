package me.elsiff.rebooted.engine.window.input

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.window.Window

/**
 * Created by elsiff on 2019-07-22.
 */
interface Input {
    val window: Window

    fun isPressed(key: Key): Boolean

    fun isClicked(mouseButton: MouseButton): Boolean

    fun mousePosition(): Vector2f
}