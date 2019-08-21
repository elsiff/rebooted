package me.elsiff.rebooted.game.world.entity

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.game.graphic.Graphic
import me.elsiff.rebooted.game.world.Camera
import me.elsiff.rebooted.game.world.World
import me.elsiff.rebooted.game.world.entity.ability.Ability
import me.elsiff.rebooted.game.world.entity.ability.AbilityRegistry
import me.elsiff.rebooted.game.world.physic.RigidBody

/**
 * Created by elsiff on 2019-07-25.
 */
interface Entity : Disposable {
    val world: World

    val rigidBody: RigidBody
    val altitude: Int
    val blockingAltitudes: IntRange

    val graphic: Graphic
    val abilities: AbilityRegistry

    var position: Vector2f
        get() = rigidBody.position
        set(value) {
            rigidBody.position = value
        }

    var velocity: Vector2f
        get() = rigidBody.velocity
        set(value) {
            rigidBody.velocity = value
        }

    fun handleCollided(other: Entity)

    fun positionIn(camera: Camera) = position - camera.origin

    fun destroy() {
        world.removeEntity(this)
        dispose()
    }
}

inline fun <reified T : Ability> Entity.findAbility() = abilities[T::class]