package me.elsiff.rebooted.engine.audio

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.math.Vector3f

/**
 * Created by elsiff on 2019-07-24.
 */
interface AudioPlayer : Disposable {
    val listener: AudioListener

    fun play(
        clip: AudioClip,
        volume: Float = 1.0f,
        pitch: Float = 1.0f,
        looping: Boolean = false,
        position: Vector3f = Vector3f.ZERO,
        direction: Vector3f = Vector3f.ZERO
    ): Sound
}