package me.elsiff.rebooted.game.world.physic

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.game.graphic.Graphic

/**
 * Created by elsiff on 2019-08-15.
 */
interface Bounding {
    val rigidBody: RigidBody
    val offset: Vector2f
    val debugGraphic: Graphic

    val position: Vector2f get() = offset + rigidBody.position
}