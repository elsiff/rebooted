package me.elsiff.rebooted.game.state

import me.elsiff.rebooted.engine.audio.AudioListener
import me.elsiff.rebooted.engine.event.EventHandler
import me.elsiff.rebooted.engine.event.EventListener
import me.elsiff.rebooted.engine.event.input.KeyPressEvent
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable.GlTexture
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable.GlTextureRegion
import me.elsiff.rebooted.engine.math.size
import me.elsiff.rebooted.engine.math.vec2f
import me.elsiff.rebooted.engine.math.vec3f
import me.elsiff.rebooted.engine.state.GameState
import me.elsiff.rebooted.engine.window.Window
import me.elsiff.rebooted.engine.window.graphic.Color
import me.elsiff.rebooted.engine.window.graphic.drawable.Rectangle
import me.elsiff.rebooted.engine.window.graphic.drawable.Text
import me.elsiff.rebooted.engine.window.input.Key
import me.elsiff.rebooted.game.graphic.Colors
import me.elsiff.rebooted.game.Game
import me.elsiff.rebooted.game.asset.AudioClips
import me.elsiff.rebooted.game.asset.Fonts
import me.elsiff.rebooted.game.asset.Textures
import me.elsiff.rebooted.game.event.HandlerLevels
import me.elsiff.rebooted.game.graphic.Layers

/**
 * Created by elsiff on 2019-03-14.
 */
class TestState : GameState {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    private val rectangle = Rectangle(
        Color.fromRGB(0xbf0000), size(30f, 30f)
    )
    private val rectangle2 = Rectangle(
        Color.fromRGB(0xFFFFFF), size(30f, 30f)
    )
    private lateinit var text: Text
    private lateinit var subWindow: Window

    override fun enter() {
        text = Text.create(font = Fonts.MONOSPACED_16, color = Colors.LIGHT_YELLOW) {
            +"Hello "
            block(color = Color.BLACK, backColor = Colors.DARK_YELLOW) {
                +"World"
            }
            +"!"
        }

        subWindow = Game.engine.createWindow("Test", size(800, 600))

        var playerPosition = vec2f(0f, 0f)
        Game.engine.eventHandlers.register(object : EventListener {
            @EventHandler(HandlerLevels.UI, ignoreCancelled = true)
            fun onKeyPressFirst(event: KeyPressEvent) {
                println(event.key.name)
                event.hasCancelled = true
            }

            @EventHandler(HandlerLevels.UI, ignoreCancelled = false)
            fun onKeyPress(event: KeyPressEvent) {
                when {
                    event.key == Key.ENTER -> {
                        Game.engine.mainAudioPlayer.play(AudioClips.TEST)
                        Game.engine.mainAudioPlayer.listener.orientation = AudioListener.Orientation(
                            at = vec3f(0f, 0f, -1f),
                            up = vec3f(0f, 1f, 0f)
                        )
                    }
                    event.key == Key.LEFT -> {
                        playerPosition -= vec2f(1f, 0f)

                        println(playerPosition)
                        Game.engine.mainAudioPlayer.listener.position = vec3f(playerPosition.x, -3f, playerPosition.y)
                    }
                    event.key == Key.RIGHT -> {
                        playerPosition += vec2f(1f, 0f)

                        println(playerPosition)
                        Game.engine.mainAudioPlayer.listener.position = vec3f(playerPosition.x, -3f, playerPosition.y)
                    }
                    event.key == Key.UP -> {
                        playerPosition += vec2f(0f, 1f)

                        println(playerPosition)
                        Game.engine.mainAudioPlayer.listener.position = vec3f(playerPosition.x, -3f, playerPosition.y)
                    }
                    event.key == Key.DOWN -> {
                        playerPosition -= vec2f(0f, 1f)

                        println(playerPosition)
                        Game.engine.mainAudioPlayer.listener.position = vec3f(playerPosition.x, -3f, playerPosition.y)
                    }
                }
            }
        })
    }

    override fun update(deltaTime: Float) {
    }

    override fun render() {
        Game.engine.mainWindow.graphics.apply {
            clear()

            add(Fonts.MONOSPACED_16.glyphOf('A').atlas, vec2f(200f, 200f), Layers.UI)
            add(rectangle, vec2f(50f, 50f), Layers.UI)
            add(rectangle, vec2f(100f, 100f), Layers.UI)
            add(Textures.TEST_TEXTURE, vec2f(200f, 100f), Layers.UI)
            add(Textures.TEST_TEXTURE_2, vec2f(200f, 200f), Layers.UI)
            add(text, vec2f(100, 150f), Layers.UI)

            render()
        }
        subWindow.graphics.apply {
            clear()

            add(rectangle, vec2f(50f, 50f), Layers.UI)
            add(rectangle2, vec2f(100f, 100f), Layers.UI)
            add(
                GlTextureRegion(
                    Textures.TEST_TEXTURE as GlTexture,
                    vec2f(12f, 12f),
                    size(24f, 24f)
                ),
                vec2f(200f, 100f),
                Layers.UI
            )
            add(Fonts.MONOSPACED_16.glyphOf('A').atlas, vec2f(200f, 200f), Layers.UI)
            add(text, vec2f(100, 150f), Layers.UI)

            render()
        }
        subWindow.update()
    }

    override fun dispose() {
        check(!_isDisposed)
        subWindow.dispose()
        _isDisposed = true
    }
}