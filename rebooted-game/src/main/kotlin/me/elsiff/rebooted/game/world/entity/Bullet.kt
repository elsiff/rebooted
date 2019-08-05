package me.elsiff.rebooted.game.world.entity

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.game.graphic.BulletGraphic
import me.elsiff.rebooted.game.graphic.Graphic
import me.elsiff.rebooted.game.world.World
import me.elsiff.rebooted.game.world.entity.ability.AbilityRegistry

/**
 * Created by elsiff on 2019-08-01.
 */
class Bullet(
    override val world: World,
    override var position: Vector2f
) : Entity {
    private var _isDisposed = false
    override val isDisposed: Boolean get() = _isDisposed

    override var velocity: Vector2f = Vector2f.ZERO
    override val drag: Float = 0.25f
    override val graphic: Graphic = BulletGraphic()
    override val abilities: AbilityRegistry = AbilityRegistry()

    override fun dispose() {
        check(!_isDisposed)

        abilities.dispose()

        _isDisposed = true
    }
}