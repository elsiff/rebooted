package me.elsiff.rebooted.engine.audio

import me.elsiff.rebooted.engine.math.Vector3f

/**
 * Created by elsiff on 2019-07-24.
 */
interface AudioListener {
    var position: Vector3f
    var velocity: Vector3f
    var orientation: Orientation

    class Orientation(
        val at: Vector3f,
        val up: Vector3f
    )
}