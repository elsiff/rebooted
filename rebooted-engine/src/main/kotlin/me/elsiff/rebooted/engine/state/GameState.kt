package me.elsiff.rebooted.engine.state

import me.elsiff.rebooted.engine.Disposable

/**
 * Created by elsiff on 2019-03-13.
 */
interface GameState : Disposable {
    fun enter()

    fun update(deltaTime: Float)

    fun render()
}