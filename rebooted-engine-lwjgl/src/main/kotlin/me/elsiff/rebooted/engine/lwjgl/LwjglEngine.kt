package me.elsiff.rebooted.engine.lwjgl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.yield
import me.elsiff.rebooted.engine.Clock
import me.elsiff.rebooted.engine.Engine
import me.elsiff.rebooted.engine.asset.AssetLoaderRegistry
import me.elsiff.rebooted.engine.asset.request.AudioClipLoadRequest
import me.elsiff.rebooted.engine.asset.request.FontLoadRequest
import me.elsiff.rebooted.engine.asset.request.TextureLoadRequest
import me.elsiff.rebooted.engine.audio.AudioPlayer
import me.elsiff.rebooted.engine.event.EventHandlerRegistry
import me.elsiff.rebooted.engine.event.EventTrigger
import me.elsiff.rebooted.engine.lwjgl.asset.AlAudioClipLoader
import me.elsiff.rebooted.engine.lwjgl.asset.GlFontLoader
import me.elsiff.rebooted.engine.lwjgl.asset.GlTextureLoader
import me.elsiff.rebooted.engine.lwjgl.audio.AlAudioPlayer
import me.elsiff.rebooted.engine.lwjgl.window.GlfwWindow
import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.state.GameState
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwTerminate
import org.slf4j.Logger

/**
 * Created by elsiff on 2019-02-26.
 */
class LwjglEngine(
    override val logger: Logger
) : Engine {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    private lateinit var _mainScope: CoroutineScope
    val mainScope: CoroutineScope get() = _mainScope

    private lateinit var glfwErrorHandler: GlfwErrorHandler
    override lateinit var mainWindow: GlfwWindow
    override lateinit var mainAudioPlayer: AudioPlayer
    override lateinit var clock: Clock

    override val assetLoaders: AssetLoaderRegistry = AssetLoaderRegistry()
        .apply {
            register(TextureLoadRequest::class, GlTextureLoader(this@LwjglEngine))
            register(FontLoadRequest::class, GlFontLoader(this@LwjglEngine))
            register(AudioClipLoadRequest::class, AlAudioClipLoader(this@LwjglEngine))
        }

    override val eventHandlers: EventHandlerRegistry = EventHandlerRegistry()
    override val eventTrigger: EventTrigger = EventTrigger(eventHandlers)

    private lateinit var _gameState: GameState
    override val gameState: GameState get() = _gameState
    override var nextState: GameState? = null

    override suspend fun start(
        windowTitle: String,
        windowSize: Size,
        initialState: GameState,
        scope: CoroutineScope
    ) {
        _mainScope = scope

        if (!glfwInit()) {
            throw GlfwException("Unable to initialize GLFW")
        }
        glfwErrorHandler = GlfwErrorHandler()
        mainWindow = createWindow(windowTitle, windowSize)
        mainAudioPlayer = createAudioPlayer()
        clock = GlfwClock()

        _gameState = initialState
        _gameState.enter()
        gameLoop()
        stop()
    }

    override fun stop() {
        dispose()
    }

    private suspend fun gameLoop() {
        while (!mainWindow.shouldClose) {
            clock.update()

            if (nextState != null) {
                if (::_gameState.isInitialized) {
                    _gameState.dispose()
                }
                _gameState = nextState!!
                nextState = null

                _gameState.enter()
            }

            _gameState.update(clock.delta)
            yield()
            _gameState.render()

            mainWindow.update()
        }
    }

    override fun dispose() {
        check(!_isDisposed)

        _gameState.dispose()
        mainWindow.dispose()
        glfwTerminate()
        glfwErrorHandler.dispose()

        _isDisposed = true
    }

    override fun createWindow(title: String, size: Size): GlfwWindow =
        GlfwWindow(this, title, size)

    override fun createAudioPlayer(): AudioPlayer =
        AlAudioPlayer()
}