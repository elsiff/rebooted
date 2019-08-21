package me.elsiff.rebooted.game

import kotlinx.coroutines.runBlocking
import me.elsiff.rebooted.engine.Engine
import me.elsiff.rebooted.engine.lwjgl.LwjglEngine
import me.elsiff.rebooted.engine.math.size
import me.elsiff.rebooted.game.state.LoadingState
import org.slf4j.LoggerFactory

/**
 * Created by elsiff on 2019-02-26.
 */
object Game {
    val engine: Engine = LwjglEngine(
        logger = LoggerFactory.getLogger(this.javaClass)
    )
    var isDebugMode: Boolean = false

    fun start() = runBlocking {
        engine.start(
            windowTitle = "HelloWorld",
            windowSize = size(800, 600),
            initialState = LoadingState(),
            scope = this
        )
    }
}