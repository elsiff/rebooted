package me.elsiff.rebooted.engine.event.input

import me.elsiff.rebooted.engine.window.input.Input
import me.elsiff.rebooted.engine.window.input.Key

/**
 * Created by elsiff on 2019-07-23.
 */
class KeyReleaseEvent(
    override val input: Input,
    override val key: Key
) : KeyEvent {
    override var hasCancelled: Boolean = false
}