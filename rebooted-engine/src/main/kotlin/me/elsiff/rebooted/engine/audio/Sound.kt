package me.elsiff.rebooted.engine.audio

import me.elsiff.rebooted.engine.math.Vector3f

/**
 * Created by elsiff on 2019-07-24.
 */
interface Sound {
    val clip: AudioClip
    val isPlaying: Boolean
    val isStopped: Boolean

    var volume: Float
    var pitch: Float
    var isLooping: Boolean
    var position: Vector3f
    var direction: Vector3f

    fun pause()

    fun resume()

    fun rewind()

    fun stop()
}