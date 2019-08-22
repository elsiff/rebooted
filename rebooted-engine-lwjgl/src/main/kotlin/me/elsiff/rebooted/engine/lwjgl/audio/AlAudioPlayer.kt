package me.elsiff.rebooted.engine.lwjgl.audio

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.audio.AudioClip
import me.elsiff.rebooted.engine.audio.AudioListener
import me.elsiff.rebooted.engine.audio.AudioPlayer
import me.elsiff.rebooted.engine.math.Vector3f
import org.lwjgl.openal.AL
import org.lwjgl.openal.AL10.alDistanceModel
import org.lwjgl.openal.AL11.AL_LINEAR_DISTANCE_CLAMPED
import org.lwjgl.openal.ALC
import org.lwjgl.openal.ALC10.*

/**
 * Created by elsiff on 2019-07-24.
 */
class AlAudioPlayer : AudioPlayer {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    private val deviceId: Long
    private val contextId: Long
    private val sounds: MutableCollection<AlSound> = mutableListOf()

    override val listener: AudioListener = AlAudioListener(this)

    init {
        val defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER)
        deviceId = alcOpenDevice(defaultDeviceName)

        val attributes = intArrayOf(0)
        contextId = alcCreateContext(deviceId, attributes)

        alcMakeContextCurrent(contextId)

        val alcCapabilities = ALC.createCapabilities(deviceId)
        val alCapabilities = AL.createCapabilities(alcCapabilities)

        check(alCapabilities.OpenAL10)

        alDistanceModel(AL_LINEAR_DISTANCE_CLAMPED)
    }

    override fun play(
        clip: AudioClip,
        volume: Float,
        pitch: Float,
        looping: Boolean,
        position: Vector3f,
        direction: Vector3f
    ): AlSound {
        require(clip is AlAudioClip)

        removeFinishedSounds()

        return AlSound(this, clip).apply {
            this.volume = volume
            this.pitch = pitch
            this.isLooping = looping
            this.position = position
            this.direction = direction
            this.play()
        }
    }

    private fun removeFinishedSounds() {
        sounds.filter { it.isStopped }.let { finishedSounds ->
            for (sound in finishedSounds) {
                sound.audioSource.dispose()
            }
            sounds.removeAll(finishedSounds)
        }
    }

    fun makeContextCurrent() {
        alcMakeContextCurrent(contextId)
    }

    override fun dispose() {
        check(!_isDisposed)

        for (sound in sounds) {
            sound.audioSource.dispose()
        }

        alcDestroyContext(contextId)
        alcCloseDevice(deviceId)

        _isDisposed = true
    }
}