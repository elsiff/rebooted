package me.elsiff.rebooted.game.world.physic

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.math.size
import me.elsiff.rebooted.engine.math.vec2f
import me.elsiff.rebooted.engine.window.graphic.drawable.Rectangle
import me.elsiff.rebooted.game.graphic.Colors
import me.elsiff.rebooted.game.graphic.Graphic
import me.elsiff.rebooted.game.graphic.Layers

/**
 * Created by elsiff on 2019-08-09.
 */
class BoundingCircle(
    override val rigidBody: RigidBody,
    val radius: Float,
    override val offset: Vector2f = Vector2f.ZERO
) : Bounding {
    override val debugGraphic: Graphic
        get() = object : Graphic {
            override val components: Collection<Graphic.Component> = listOf(
                Graphic.Component(
                    drawing = Rectangle(Colors.WHITE, size(radius * 2, radius * 2)),
                    offset = offset + vec2f(-radius, -radius),
                    layer = Layers.UI
                )
            )
        }
}