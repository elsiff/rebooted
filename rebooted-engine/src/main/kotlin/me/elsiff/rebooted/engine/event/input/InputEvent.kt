package me.elsiff.rebooted.engine.event.input

import me.elsiff.rebooted.engine.event.Cancellable
import me.elsiff.rebooted.engine.event.Event
import me.elsiff.rebooted.engine.window.Window
import me.elsiff.rebooted.engine.window.input.Input

/**
 * Created by elsiff on 2019-07-23.
 */
interface InputEvent : Event, Cancellable {
    val input: Input

    val window: Window get() = input.window
}