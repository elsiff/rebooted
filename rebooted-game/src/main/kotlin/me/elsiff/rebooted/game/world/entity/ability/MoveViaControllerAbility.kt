package me.elsiff.rebooted.game.world.entity.ability

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import me.elsiff.rebooted.engine.event.EventHandler
import me.elsiff.rebooted.engine.event.EventListener
import me.elsiff.rebooted.engine.event.input.KeyPressEvent
import me.elsiff.rebooted.engine.event.input.KeyReleaseEvent
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.math.vec2f
import me.elsiff.rebooted.engine.window.input.Key
import me.elsiff.rebooted.game.Game
import me.elsiff.rebooted.game.event.HandlerLevels
import me.elsiff.rebooted.game.event.WorldUpdateEvent
import me.elsiff.rebooted.game.world.entity.Entity

/**
 * Created by elsiff on 2019-07-30.
 */
class MoveViaControllerAbility(
    private val entity: Entity,
    private val speed: Float
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

    inner class Listener : EventListener, CoroutineScope by CoroutineScope(Dispatchers.Default) {
        private val movingDirections: MutableSet<Direction> = mutableSetOf()

        @EventHandler(HandlerLevels.USER_CONTROLLER)
        fun onKeyPress(event: KeyPressEvent) {
            val direction = Direction.fromWasd(event.key)
            if (direction != null) {
                movingDirections += direction
            }
        }

        @EventHandler(HandlerLevels.USER_CONTROLLER)
        fun onWorldUpdate(event: WorldUpdateEvent) {
            if (event.world == entity.world) {
                for (direction in movingDirections) {
                    val delta = direction.vector * speed * event.deltaTime
                    entity.position += delta
                }
            }
        }

        @EventHandler(HandlerLevels.USER_CONTROLLER)
        fun onKeyRelease(event: KeyReleaseEvent) {
            val direction = Direction.fromWasd(event.key)
            if (direction != null) {
                movingDirections -= direction
            }
        }
    }

    private enum class Direction(
        val vector: Vector2f
    ) {
        EAST(vec2f(1f, 0f)),
        WEST(vec2f(-1f, 0f)),
        SOUTH(vec2f(0f, -1f)),
        NORTH(vec2f(0f, 1f));

        companion object {
            fun fromWasd(key: Key): Direction? {
                return when (key) {
                    Key.W -> NORTH
                    Key.A -> WEST
                    Key.S -> SOUTH
                    Key.D -> EAST
                    else -> null
                }
            }
        }
    }
}