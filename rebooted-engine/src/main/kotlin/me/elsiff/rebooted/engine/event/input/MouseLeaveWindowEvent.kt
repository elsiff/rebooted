package me.elsiff.rebooted.engine.event.input

import me.elsiff.rebooted.engine.window.input.Input

/**
 * Created by elsiff on 2019-07-23.
 */
class MouseLeaveWindowEvent(
    override val input: Input
) : MouseEvent {
    override var hasCancelled: Boolean = false
}