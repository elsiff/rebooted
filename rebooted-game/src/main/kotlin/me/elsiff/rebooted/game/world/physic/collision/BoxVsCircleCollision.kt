package me.elsiff.rebooted.game.world.physic.collision

import me.elsiff.rebooted.engine.math.MutableVector2f
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.math.clamp
import me.elsiff.rebooted.engine.math.mutableVec2f
import me.elsiff.rebooted.game.world.physic.AABB
import me.elsiff.rebooted.game.world.physic.BoundingCircle
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Created by elsiff on 2019-08-12.
 */
class BoxVsCircleCollision private constructor(
    override val normal: Vector2f,
    override val penetration: Float
) : Collision {
    companion object {
        fun between(a: AABB, b: BoundingCircle): BoxVsCircleCollision? {
            val aToB = b.position - a.position
            val r = b.radius

            if (b.isInside(a)) {
                val closestToB = aToB - a.closestPointTo(b)
                val d = closestToB.length

                return BoxVsCircleCollision(
                    normal = -closestToB.normalized(),
                    penetration = r - d
                )
            } else {
                val closestToB = aToB - a.closestPointTo(b)
                val d2 = closestToB.lengthSquared

                if (d2 > (r * r))
                    return null

                val d = sqrt(d2)
                return BoxVsCircleCollision(
                    normal = closestToB.normalized(),
                    penetration = r - d
                )
            }
        }

        private fun BoundingCircle.isInside(other: AABB): Boolean {
            return (this.position.x in (other.min.x)..(other.max.x) &&
                    this.position.y in (other.min.y)..(other.max.y))
        }

        private fun AABB.closestPointTo(other: BoundingCircle): Vector2f {
            val diff = other.position - this.position

            val extentX = (this.max.x - this.min.x) / 2f
            val extentY = (this.max.y - this.min.y) / 2f

            val closest = mutableVec2f(
                x = clamp(diff.x, -extentX, extentX),
                y = clamp(diff.y, -extentY, extentY)
            )

            if (other.isInside(this)) {
                val closestAxis = if (abs(diff.x) > abs(diff.y)) Axis.X else Axis.Y
                when (closestAxis) {
                    Axis.X -> closest.extendXToCloseEdge(extentX)
                    Axis.Y -> closest.extendYToCloseEdge(extentY)
                }
            }
            return closest
        }

        private fun MutableVector2f.extendXToCloseEdge(extentX: Float) {
            x = if (x > 0) extentX else -extentX
        }

        private fun MutableVector2f.extendYToCloseEdge(extentY: Float) {
            y = if (y > 0) extentY else -extentY
        }

        private enum class Axis { X, Y }
    }
}