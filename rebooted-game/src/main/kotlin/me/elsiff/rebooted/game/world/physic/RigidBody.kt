package me.elsiff.rebooted.game.world.physic

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.game.world.physic.collision.Collision

/**
 * Created by elsiff on 2019-08-14.
 */
class RigidBody(
    var position: Vector2f = Vector2f.ZERO,
    var velocity: Vector2f = Vector2f.ZERO,
    val mass: Float,
    val restitution: Float,
    val drag: Float
) {
    val boundings: MutableList<Bounding> = mutableListOf()
    val inverseMass: Float = 1f / mass

    fun findCollisionWith(other: RigidBody): Collision? {
        for (a in boundings) {
            for (b in other.boundings) {
                val collision = Collision.between(a, b)

                if (collision != null) {
                    return collision
                }
            }
        }
        return null
    }
}