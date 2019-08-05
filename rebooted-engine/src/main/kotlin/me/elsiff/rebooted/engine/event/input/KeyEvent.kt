package me.elsiff.rebooted.engine.event.input

import me.elsiff.rebooted.engine.window.input.Key

/**
 * Created by elsiff on 2019-07-23.
 */
interface KeyEvent : InputEvent {
    val key: Key
}