package me.elsiff.rebooted.engine.window.graphic.drawable

import me.elsiff.rebooted.engine.math.Vector2f

/**
 * Created by elsiff on 2019-02-28.
 */
interface Drawable {
    open class DrawingOption(
        val position: Vector2f,
        val drawingNumber: Int
    )
}