package me.elsiff.rebooted.engine

import kotlinx.coroutines.CoroutineScope
import me.elsiff.rebooted.engine.asset.AssetLoaderRegistry
import me.elsiff.rebooted.engine.audio.AudioPlayer
import me.elsiff.rebooted.engine.event.EventHandlerRegistry
import me.elsiff.rebooted.engine.event.EventTrigger
import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.state.GameState
import me.elsiff.rebooted.engine.window.Window
import org.slf4j.Logger

/**
 * Created by elsiff on 2019-02-27.
 */
interface Engine : Disposable {
    val logger: Logger
    val mainWindow: Window
    val mainAudioPlayer: AudioPlayer
    val clock: Clock
    val assetLoaders: AssetLoaderRegistry
    val eventHandlers: EventHandlerRegistry
    val eventTrigger: EventTrigger

    val gameState: GameState
    var nextState: GameState?

    suspend fun start(
        windowTitle: String, windowSize: Size, initialState: GameState,
        scope: CoroutineScope
    )

    fun stop()

    fun createWindow(title: String, size: Size): Window

    fun createAudioPlayer(): AudioPlayer
}