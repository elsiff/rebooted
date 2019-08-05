package me.elsiff.rebooted.engine.lwjgl.window.graphic.drawer

import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.Batch
import me.elsiff.rebooted.engine.window.graphic.drawable.Drawable

/**
 * Created by elsiff on 2019-02-28.
 */
interface Drawer {
    fun draw(thing: Drawable, batch: Batch, option: Drawable.DrawingOption)
}