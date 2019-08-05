package me.elsiff.rebooted.game.world.entity.ability

import me.elsiff.rebooted.engine.Disposable

/**
 * Created by elsiff on 2019-07-26.
 */
interface Ability : Disposable {
    val isEnabled: Boolean

    fun enable()
    fun disable()
}