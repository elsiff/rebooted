package me.elsiff.rebooted.game.world.physic.collision

import me.elsiff.rebooted.engine.math.EPSILON
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.math.vec2f
import me.elsiff.rebooted.game.world.physic.BoundingCircle
import kotlin.math.sqrt

/**
 * Created by elsiff on 2019-08-09.
 */
class CircleVsCircleCollision private constructor(
    override val normal: Vector2f,
    override val penetration: Float
) : Collision {
    companion object {
        fun between(a: BoundingCircle, b: BoundingCircle): CircleVsCircleCollision? {
            val aToB = b.position - a.position

            val r = a.radius + b.radius
            val r2 = r * r
            val len2 = aToB.lengthSquared

            if (len2 > r2)
                return null

            val distance = sqrt(len2)
            return if (distance > EPSILON) {
                CircleVsCircleCollision(
                    normal = aToB / distance,
                    penetration = r2 - distance
                )
            } else {
                CircleVsCircleCollision(
                    normal = vec2f(1f, 0f),
                    penetration = a.radius
                )
            }
        }
    }
}