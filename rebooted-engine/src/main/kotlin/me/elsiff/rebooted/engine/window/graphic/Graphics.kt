package me.elsiff.rebooted.engine.window.graphic

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.window.Window
import me.elsiff.rebooted.engine.window.graphic.drawable.Drawable

/**
 * Created by elsiff on 2019-02-27.
 */
interface Graphics : Disposable {
    val window: Window

    fun clear()

    fun add(drawing: Drawable, position: Vector2f, drawingNumber: Int)

    fun render()
}