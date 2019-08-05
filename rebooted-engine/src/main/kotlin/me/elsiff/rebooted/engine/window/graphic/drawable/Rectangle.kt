package me.elsiff.rebooted.engine.window.graphic.drawable

import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.window.graphic.Color

/**
 * Created by elsiff on 2019-02-28.
 */
data class Rectangle(
    val color: Color,
    val size: Size
) : Drawable