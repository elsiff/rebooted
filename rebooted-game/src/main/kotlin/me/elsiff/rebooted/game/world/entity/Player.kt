package me.elsiff.rebooted.game.world.entity

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.game.graphic.Graphic
import me.elsiff.rebooted.game.graphic.PlayerGraphic
import me.elsiff.rebooted.game.world.World
import me.elsiff.rebooted.game.world.entity.ability.AbilityRegistry
import me.elsiff.rebooted.game.world.entity.ability.MoveViaControllerAbility
import me.elsiff.rebooted.game.world.entity.ability.ShootShotgunAbility

/**
 * Created by elsiff on 2019-07-29.
 */
class Player(
    override val world: World,
    override var position: Vector2f
) : Entity {
    private var _isDisposed = false
    override val isDisposed: Boolean get() = _isDisposed

    override var velocity: Vector2f = Vector2f.ZERO
    override val drag = 0.35f

    override val graphic: Graphic = PlayerGraphic()
    override val abilities: AbilityRegistry = AbilityRegistry()
        .apply {
            register(MoveViaControllerAbility(this@Player, 100f))
            register(ShootShotgunAbility(this@Player))
        }

    override fun dispose() {
        check(!_isDisposed)

        abilities.dispose()

        _isDisposed = true
    }
}