package me.elsiff.rebooted.engine.lwjgl.audio

import me.elsiff.rebooted.engine.audio.AudioListener
import me.elsiff.rebooted.engine.math.Vector3f
import me.elsiff.rebooted.engine.math.vec3f
import org.lwjgl.openal.AL10.*

/**
 * Created by elsiff on 2019-07-24.
 */
class AlAudioListener(
    private val audioPlayer: AlAudioPlayer
) : AudioListener {
    override var position: Vector3f
        get() {
            audioPlayer.makeContextCurrent()
            val arr = FloatArray(3)
            alGetListenerfv(AL_POSITION, arr)
            return vec3f(arr[0], arr[1], arr[2])
        }
        set(value) {
            audioPlayer.makeContextCurrent()
            alListener3f(AL_POSITION, value.x, value.y, value.z)
        }

    override var velocity: Vector3f
        get() {
            audioPlayer.makeContextCurrent()
            val arr = FloatArray(3)
            alGetListenerfv(AL_VELOCITY, arr)
            return vec3f(arr[0], arr[1], arr[2])
        }
        set(value) {
            audioPlayer.makeContextCurrent()
            alListener3f(AL_VELOCITY, value.x, value.y, value.z)
        }

    override var orientation: AudioListener.Orientation
        get() {
            audioPlayer.makeContextCurrent()
            val arr = FloatArray(6)
            alGetListenerfv(AL_ORIENTATION, arr)
            return AudioListener.Orientation(
                at = vec3f(arr[0], arr[1], arr[2]),
                up = vec3f(arr[4], arr[5], arr[6])
            )
        }
        set(value) {
            audioPlayer.makeContextCurrent()
            val arr = floatArrayOf(
                value.at.x, value.at.y, value.at.z,
                value.up.x, value.up.y, value.up.z
            )
            alListenerfv(AL_ORIENTATION, arr)
        }
}