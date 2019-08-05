package me.elsiff.rebooted.game.world.entity.ability

import me.elsiff.rebooted.engine.event.EventHandler
import me.elsiff.rebooted.engine.event.EventListener
import me.elsiff.rebooted.engine.event.input.MouseClickEvent
import me.elsiff.rebooted.engine.window.input.MouseButton
import me.elsiff.rebooted.game.Game
import me.elsiff.rebooted.game.event.HandlerLevels
import me.elsiff.rebooted.game.world.entity.Bullet
import me.elsiff.rebooted.game.world.entity.Entity

/**
 * Created by elsiff on 2019-08-01.
 */
class ShootShotgunAbility(
    private val entity: Entity
) : Ability {
    private var _isDisposed = false
    override val isDisposed: Boolean get() = _isDisposed

    private var _isEnabled = false
    override val isEnabled: Boolean get() = _isEnabled

    private val listener: Listener = Listener()

    override fun enable() {
        check(!_isEnabled)

        Game.engine.eventHandlers.register(listener)

        _isEnabled = true
    }

    override fun disable() {
        check(_isEnabled)

        Game.engine.eventHandlers.unregister(listener)

        _isEnabled = false
    }

    override fun dispose() {
        check(!_isDisposed)

        if (_isEnabled)
            disable()

        _isDisposed = true
    }

    inner class Listener : EventListener {
        @EventHandler(HandlerLevels.USER_CONTROLLER)
        fun onMouseClick(event: MouseClickEvent) {
            if (event.mouseButton == MouseButton.LEFT) {
                val direction = (event.mousePosition - entity.position).normalized()

                val bullet = Bullet(entity.world, entity.position)
                entity.world += bullet

                bullet.velocity = direction * 100.0f
            }
        }
    }
}