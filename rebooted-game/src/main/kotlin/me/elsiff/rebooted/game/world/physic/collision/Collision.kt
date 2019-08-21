package me.elsiff.rebooted.game.world.physic.collision

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.game.world.physic.AABB
import me.elsiff.rebooted.game.world.physic.Bounding
import me.elsiff.rebooted.game.world.physic.BoundingCircle

/**
 * Created by elsiff on 2019-08-09.
 */
interface Collision {
    val normal: Vector2f
    val penetration: Float

    companion object {
        fun between(a: Bounding, b: Bounding): Collision? {
            if (a is AABB) {
                if (b is AABB)
                    return BoxVsBoxCollision.between(a, b)
                if (b is BoundingCircle)
                    return BoxVsCircleCollision.between(a, b)
            }
            if (a is BoundingCircle) {
                if (b is AABB)
                    return BoxVsCircleCollision.between(b, a)
                if (b is BoundingCircle)
                    return CircleVsCircleCollision.between(a, b)
            }
            throw IllegalArgumentException("An invalid bounding")
        }
    }
}