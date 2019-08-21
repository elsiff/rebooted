package me.elsiff.rebooted.game.world.physic

import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.window.graphic.drawable.Rectangle
import me.elsiff.rebooted.game.graphic.Colors
import me.elsiff.rebooted.game.graphic.Graphic
import me.elsiff.rebooted.game.graphic.Layers

/**
 * Created by elsiff on 2019-08-09.
 */
class AABB(
    override val rigidBody: RigidBody,
    val size: Size,
    override val offset: Vector2f = Vector2f.ZERO
) : Bounding {
    private val sizeHalf: Vector2f = size.toVector2f() / 2f

    val min: Vector2f get() = this.position - sizeHalf
    val max: Vector2f get() = this.position + sizeHalf

    override val debugGraphic: Graphic
        get() = object : Graphic {
            override val components: Collection<Graphic.Component> = listOf(
                Graphic.Component(
                    drawing = Rectangle(Colors.WHITE, size),
                    offset = offset - sizeHalf,
                    layer = Layers.UI
                )
            )
        }
}