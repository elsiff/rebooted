package me.elsiff.rebooted.game.world.physic.collision

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.math.vec2f
import me.elsiff.rebooted.game.world.physic.AABB
import kotlin.math.abs

/**
 * Created by elsiff on 2019-08-09.
 */
class BoxVsBoxCollision private constructor(
    override val normal: Vector2f,
    override val penetration: Float
) : Collision {
    companion object {
        fun between(a: AABB, b: AABB): BoxVsBoxCollision? {
            if (intersects(a, b)) {
                val aToB = b.position - a.position

                val overlapX = overlapX(a, b, aToB)
                if (overlapX < 0)
                    return null

                val overlapY = overlapY(a, b, aToB)
                if (overlapY < 0)
                    return null

                return if (overlapX > overlapY) {
                    BoxVsBoxCollision(
                        normal = when (aToB.x < 0) {
                            true -> vec2f(-1f, 0f)
                            false -> vec2f(1f, 0f)
                        },
                        penetration = overlapX
                    )
                } else {
                    BoxVsBoxCollision(
                        normal = when (aToB.y < 0) {
                            true -> vec2f(0f, -1f)
                            false -> vec2f(0f, 1f)
                        },
                        penetration = overlapY
                    )
                }
            }
            return null
        }

        private fun intersects(a: AABB, b: AABB): Boolean {
            if (a.max.x < b.min.x || a.min.x > b.max.x) return false
            if (a.max.y < b.min.y || a.min.y > b.max.y) return false
            return true
        }

        private fun overlapX(a: AABB, b: AABB, aToB: Vector2f): Float {
            val extentA = (a.max.x - a.min.x) / 2f
            val extentB = (b.max.x - b.min.x) / 2f
            return (extentA + extentB - abs(aToB.x))
        }

        private fun overlapY(a: AABB, b: AABB, aToB: Vector2f): Float {
            val extentA = (a.max.y - a.min.y) / 2f
            val extentB = (b.max.y - b.min.y) / 2f
            return (extentA + extentB - abs(aToB.y))
        }
    }
}