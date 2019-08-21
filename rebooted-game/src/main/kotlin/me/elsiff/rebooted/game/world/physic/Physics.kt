package me.elsiff.rebooted.game.world.physic

import me.elsiff.rebooted.game.Game
import me.elsiff.rebooted.game.event.CollisionOccurredEvent
import me.elsiff.rebooted.game.world.World
import me.elsiff.rebooted.game.world.entity.Entity
import me.elsiff.rebooted.game.world.physic.collision.Collision
import kotlin.math.max
import kotlin.math.min

/**
 * Created by elsiff on 2019-07-25.
 */
class Physics(
    private val world: World
) {
    fun update(deltaTime: Float) {
        for (entity in world.entities) {
            if (!entity.isDisposed) {
                updatePosition(entity.rigidBody, deltaTime)
                resolveCollisions(entity)
            }
        }
    }

    private fun updatePosition(rigidBody: RigidBody, deltaTime: Float) {
        val velLen2 = rigidBody.velocity.lengthSquared

        if (velLen2 > 0) {
            rigidBody.velocity *= (1f - deltaTime * rigidBody.drag)
            rigidBody.position += rigidBody.velocity * deltaTime
        }
    }

    fun resolveCollisions(entity: Entity) {
        for (other in world.entities) {
            if (entity !== other && !entity.isDisposed) {
                val rigidBodyA = entity.rigidBody
                val rigidBodyB = other.rigidBody

                val collision = rigidBodyA.findCollisionWith(rigidBodyB)
                if (collision != null) {
                    resolveCollision(entity, other, collision)
                }
            }
        }
    }

    fun resolveCollision(a: Entity, b: Entity, collision: Collision) {
        if (
            a.altitude in b.blockingAltitudes ||
            b.altitude in a.blockingAltitudes
        ) {
            applyImpulse(a.rigidBody, b.rigidBody, collision)
        }

        a.handleCollided(b)
        b.handleCollided(a)

        Game.engine.eventTrigger.call(CollisionOccurredEvent(collision, setOf(a, b)))
    }

    private fun applyImpulse(a: RigidBody, b: RigidBody, collision: Collision) {
        val relative = b.velocity - a.velocity
        val relativeAlongNormal = relative dot collision.normal

        if (relativeAlongNormal < 0) {
            val e = min(a.restitution, b.restitution)
            val j = (-(1f + e) * relativeAlongNormal) / (a.inverseMass + b.inverseMass)

            val impulse = collision.normal * j

            a.velocity -= impulse * a.inverseMass
            b.velocity += impulse * b.inverseMass

            correctPositions(a, b, collision)
        }
    }

    private fun correctPositions(a: RigidBody, b: RigidBody, collision: Collision) {
        val correction = max(collision.penetration - SLOP, 0f) /
                (a.inverseMass + b.inverseMass) * CORRECT_PERCENT

        a.position -= a.inverseMass * correction
        b.position += b.inverseMass * correction
    }

    companion object {
        const val CORRECT_PERCENT = 0.2f
        const val SLOP = 0.01f
    }
}