package me.elsiff.rebooted.engine.event.input

import me.elsiff.rebooted.engine.math.Vector2f

/**
 * Created by elsiff on 2019-07-23.
 */
interface MouseEvent : InputEvent {
    val mousePosition: Vector2f get() = input.mousePosition()
}