package me.elsiff.rebooted.game.graphic

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.window.graphic.Graphics
import me.elsiff.rebooted.engine.window.graphic.drawable.Drawable

/**
 * Created by elsiff on 2019-07-26.
 */
interface Graphic {
    val components: Collection<Component>

    class Component(
        val drawing: Drawable,
        val offset: Vector2f,
        val layer: Int
    )

    object Empty : Graphic {
        override val components: Collection<Component> = emptyList()
    }
}

fun Graphics.add(graphic: Graphic, position: Vector2f) {
    for (component in graphic.components) {
        this.add(
            drawing = component.drawing,
            position = position + component.offset,
            drawingNumber = component.layer
        )
    }
}