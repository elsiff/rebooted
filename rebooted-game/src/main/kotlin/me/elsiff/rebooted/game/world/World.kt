package me.elsiff.rebooted.game.world

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.audio.AudioClip
import me.elsiff.rebooted.engine.audio.AudioListener
import me.elsiff.rebooted.engine.audio.AudioPlayer
import me.elsiff.rebooted.engine.audio.Sound
import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.math.Vector3f
import me.elsiff.rebooted.engine.math.vec3f
import me.elsiff.rebooted.game.Game
import me.elsiff.rebooted.game.event.WorldUpdateEvent
import me.elsiff.rebooted.game.world.entity.Entity
import me.elsiff.rebooted.game.world.physic.Physics


/**
 * Created by elsiff on 2019-07-25.
 */
class World(
    val size: Size
) : Disposable {
    private var _isDisposed = false
    override val isDisposed: Boolean get() = _isDisposed

    val audioPlayer: AudioPlayer = Game.engine.createAudioPlayer()
    val physics: Physics = Physics(this)

    private val _entities: MutableList<Entity> = mutableListOf()
    val entities: List<Entity> get() = _entities

    init {
        audioPlayer.listener.orientation = AudioListener.Orientation(
            at = vec3f(0f, 0f, -1f),
            up = vec3f(0f, 1f, 0f)
        )
    }

    fun update(deltaTime: Float) {
        Game.engine.eventTrigger.call(WorldUpdateEvent(this, deltaTime))

        for (entity in _entities) {
            val velLen2 = entity.velocity.lengthSquared()

            if (velLen2 > 0) {
                entity.velocity *= (1 - deltaTime * entity.drag)
                entity.position += entity.velocity * deltaTime
            }
        }
    }

    fun addEntity(entity: Entity) {
        require(!_entities.contains(entity))
        _entities += entity
    }

    operator fun plusAssign(entity: Entity) = addEntity(entity)

    fun removeEntity(entity: Entity) {
        require(_entities.contains(entity))
        _entities -= entity
    }

    operator fun minusAssign(entity: Entity) = removeEntity(entity)

    fun playSound(
        clip: AudioClip,
        position: Vector3f,
        direction: Vector3f = Vector3f.ZERO,
        volume: Float = 1.0f,
        pitch: Float = 1.0f
    ): Sound {
        return audioPlayer.play(
            clip = clip,
            position = position,
            direction = direction,
            volume = volume,
            pitch = pitch
        )
    }

    override fun dispose() {
        check(!_isDisposed)

        for (entity in _entities) {
            entity.dispose()
        }
        _entities.clear()

        _isDisposed = true
    }
}