package me.elsiff.rebooted.engine.event.input

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.window.input.Input

/**
 * Created by elsiff on 2019-07-23.
 */
class MouseMoveEvent(
    override val input: Input,
    val newPosition: Vector2f
) : MouseEvent {
    override var hasCancelled: Boolean = false

    val oldPosition: Vector2f get() = mousePosition
}