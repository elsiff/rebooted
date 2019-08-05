package me.elsiff.rebooted.engine.lwjgl.audio

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.math.Vector3f
import me.elsiff.rebooted.engine.math.vec3f
import org.lwjgl.openal.AL10.*

/**
 * Created by elsiff on 2019-07-24.
 */
class AudioSource(
    private val player: AlAudioPlayer,
    clip: AlAudioClip
) : Disposable {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    private val id = alGenSources()

    val isPlaying: Boolean
        get() {
            player.makeContextCurrent()
            return alGetSourcei(id, AL_SOURCE_STATE) == AL_PLAYING
        }

    val isStopped: Boolean
        get() {
            player.makeContextCurrent()
            return alGetSourcei(id, AL_SOURCE_STATE) == AL_STOPPED
        }

    var volume: Float
        get() {
            player.makeContextCurrent()
            return alGetSourcef(id, AL_GAIN)
        }
        set(value) {
            player.makeContextCurrent()
            alSourcef(id, AL_GAIN, value)
        }

    var pitch: Float
        get() {
            player.makeContextCurrent()
            return alGetSourcef(id, AL_PITCH)
        }
        set(value) {
            player.makeContextCurrent()
            alSourcef(id, AL_PITCH, value)
        }

    var isLooping: Boolean
        get() {
            player.makeContextCurrent()
            return alGetSourcei(id, AL_LOOPING) == AL_TRUE
        }
        set(value) {
            player.makeContextCurrent()
            alSourcei(id, AL_LOOPING, if (value) AL_TRUE else AL_FALSE)
        }

    var position: Vector3f
        get() {
            player.makeContextCurrent()
            val arr = FloatArray(3)
            alGetSourcefv(id, AL_POSITION, arr)
            return vec3f(arr[0], arr[1], arr[2])
        }
        set(value) {
            player.makeContextCurrent()
            alSource3f(id, AL_POSITION, value.x, value.y, value.z)
        }

    var direction: Vector3f
        get() {
            player.makeContextCurrent()
            val arr = FloatArray(3)
            alGetSourcefv(id, AL_DIRECTION, arr)
            return vec3f(arr[0], arr[1], arr[2])
        }
        set(value) {
            player.makeContextCurrent()
            alSource3f(id, AL_DIRECTION, value.x, value.y, value.z)
        }

    init {
        player.makeContextCurrent()
        alSourcei(id, AL_BUFFER, clip.buffer.id)
        alSourcei(id, AL_SOURCE_ABSOLUTE, AL_TRUE)
    }

    fun play() {
        player.makeContextCurrent()
        alSourcePlay(id)
    }

    fun pause() {
        player.makeContextCurrent()
        alSourcePause(id)
    }

    fun rewind() {
        player.makeContextCurrent()
        alSourceRewind(id)
    }

    fun stop() {
        player.makeContextCurrent()
        alSourceStop(id)
    }

    override fun dispose() {
        check(!_isDisposed)

        player.makeContextCurrent()
        alDeleteSources(id)

        _isDisposed = true
    }
}