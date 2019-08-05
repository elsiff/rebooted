package me.elsiff.rebooted.engine.event

/**
 * Created by elsiff on 2019-07-22.
 */
annotation class EventHandler(
    val handlingNumber: Int,
    val ignoreCancelled: Boolean = false
)