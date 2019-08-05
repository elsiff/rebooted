package me.elsiff.rebooted.game.world.entity

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.game.graphic.Graphic
import me.elsiff.rebooted.game.world.Camera
import me.elsiff.rebooted.game.world.World
import me.elsiff.rebooted.game.world.entity.ability.Ability
import me.elsiff.rebooted.game.world.entity.ability.AbilityRegistry

/**
 * Created by elsiff on 2019-07-25.
 */
interface Entity : Disposable {
    val world: World
    var position: Vector2f
    var velocity: Vector2f
    val drag: Float
    val graphic: Graphic
    val abilities: AbilityRegistry

    fun positionIn(camera: Camera) = position - camera.origin

    fun destroy() {
        world.removeEntity(this)
        dispose()
    }
}

inline fun <reified T : Ability> Entity.findAbility() = abilities[T::class]