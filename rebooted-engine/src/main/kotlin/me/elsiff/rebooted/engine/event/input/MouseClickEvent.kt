package me.elsiff.rebooted.engine.event.input

import me.elsiff.rebooted.engine.window.input.Input
import me.elsiff.rebooted.engine.window.input.MouseButton

/**
 * Created by elsiff on 2019-07-23.
 */
class MouseClickEvent(
    override val input: Input,
    val mouseButton: MouseButton
) : MouseEvent {
    override var hasCancelled: Boolean = false

    fun isLeftClick() = mouseButton == MouseButton.LEFT
    fun isRightClick() = mouseButton == MouseButton.RIGHT
}