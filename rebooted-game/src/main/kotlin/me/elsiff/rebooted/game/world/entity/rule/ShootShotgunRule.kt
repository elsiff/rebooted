package me.elsiff.rebooted.game.world.entity.rule

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
class ShootShotgunRule(
    override val entity: Entity
) : EntityRule {
    private var _isDisposed = false
    override val isDisposed: Boolean get() = _isDisposed

    private val listener: Listener = Listener()

    init {
        Game.engine.eventHandlers.register(listener)
    }

    override fun dispose() {
        check(!_isDisposed)

        Game.engine.eventHandlers.unregister(listener)

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