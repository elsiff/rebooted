package me.elsiff.rebooted.engine.lwjgl.audio

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.audio.Sound
import me.elsiff.rebooted.engine.math.Vector3f

/**
 * Created by elsiff on 2019-07-24.
 */
class AlSound(
    player: AlAudioPlayer,
    override val clip: AlAudioClip
) : Sound, Disposable {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    val audioSource: AudioSource = AudioSource(player, clip)

    override val isPlaying: Boolean
        get() = !audioSource.isDisposed && audioSource.isPlaying

    override val isStopped: Boolean
        get() = audioSource.isDisposed || audioSource.isStopped

    override var volume: Float
        get() {
            check(!audioSource.isDisposed)
            return audioSource.volume
        }
        set(value) {
            check(!audioSource.isDisposed)
            audioSource.volume = value
        }

    override var pitch: Float
        get() {
            check(!audioSource.isDisposed)
            return audioSource.pitch
        }
        set(value) {
            check(!audioSource.isDisposed)
            audioSource.pitch = value
        }

    override var isLooping: Boolean
        get() {
            check(!audioSource.isDisposed)
            return audioSource.isLooping
        }
        set(value) {
            check(!audioSource.isDisposed)
            audioSource.isLooping = value
        }

    override var position: Vector3f
        get() {
            check(!audioSource.isDisposed)
            return audioSource.position
        }
        set(value) {
            check(!audioSource.isDisposed)
            audioSource.position = value
        }

    override var direction: Vector3f
        get() {
            check(!audioSource.isDisposed)
            return audioSource.direction
        }
        set(value) {
            check(!audioSource.isDisposed)
            audioSource.direction = value
        }

    override fun pause() {
        check(!audioSource.isDisposed)
        audioSource.pause()
    }

    fun play() {
        check(!audioSource.isDisposed)
        audioSource.play()
    }

    override fun resume() = play()

    override fun rewind() {
        check(!audioSource.isDisposed)
        audioSource.rewind()
    }

    override fun stop() {
        check(!audioSource.isDisposed)
        audioSource.stop()
    }

    override fun dispose() {
        check(!_isDisposed)
        check(!audioSource.isDisposed)
        audioSource.dispose()
    }
}