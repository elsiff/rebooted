package me.elsiff.rebooted.engine

/**
 * Created by elsiff on 2019-02-27.
 */
interface Clock {
    val currentTime: Double
    val delta: Float

    fun update()
}