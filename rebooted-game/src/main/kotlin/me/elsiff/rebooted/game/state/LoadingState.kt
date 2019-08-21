package me.elsiff.rebooted.game.state

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.yield
import me.elsiff.rebooted.engine.lwjgl.LwjglEngine
import me.elsiff.rebooted.engine.math.vec2f
import me.elsiff.rebooted.engine.state.GameState
import me.elsiff.rebooted.engine.window.graphic.drawable.Text
import me.elsiff.rebooted.game.graphic.Colors
import me.elsiff.rebooted.game.Game
import me.elsiff.rebooted.game.asset.AssetAssigner
import me.elsiff.rebooted.game.asset.AudioClips
import me.elsiff.rebooted.game.asset.Fonts
import me.elsiff.rebooted.game.asset.Textures
import me.elsiff.rebooted.game.graphic.Layers
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties

/**
 * Created by elsiff on 2019-07-14.
 */
class LoadingState : GameState {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    private val assetAssigner: AssetAssigner = AssetAssigner()
    private lateinit var loadingJob: Job
    private lateinit var changingStateJob: Job

    private val mutex: Mutex = Mutex()
    private lateinit var text: Text

    override fun enter() {
        val basicProperties = listOf<KMutableProperty<*>>(
            Fonts::MONOSPACED_16
        )

        val basicPropertiesNames = basicProperties.map { it.name }
        fun assetPropertiesOf(owner: Any): Map<KMutableProperty<*>, Any> {
            return owner::class.declaredMemberProperties
                .filterIsInstance<KMutableProperty<*>>()
                .filter { it.name !in basicPropertiesNames }
                .map { Pair(it, owner) }
                .toMap()
        }

        val followingProperties = mutableMapOf<KMutableProperty<*>, Any>()
        followingProperties += assetPropertiesOf(Textures)
        followingProperties += assetPropertiesOf(Fonts)
        followingProperties += assetPropertiesOf(AudioClips)

        loadingJob = (Game.engine as LwjglEngine).mainScope.launch {
            basicProperties.forEach { assetAssigner.assignAssetTo(it) }
            updateLoadingText(0f)

            loadAssets(followingProperties)
        }
    }

    private suspend fun loadAssets(properties: Map<KMutableProperty<*>, Any>) {
        var loadedAmount = 0
        for ((property, owner) in properties.entries) {
            mutex.withLock {
                updateLoadingText(loadedAmount / properties.size.toFloat())
            }

            assetAssigner.assignAssetTo(property, owner)
            loadedAmount++
            yield()
        }
    }

    override fun update(deltaTime: Float) {
        if (loadingJob.isCompleted && !::changingStateJob.isInitialized) {
            updateLoadingText(1.0f)
            changingStateJob = (Game.engine as LwjglEngine).mainScope.launch {
                delay(100L)
                Game.engine.nextState = WorldState()
            }
        }
    }

    override fun render() {
        Game.engine.mainWindow.graphics.apply {
            clear()

            if (::text.isInitialized) {
                add(text, vec2f(0f, window.size.height - 16f), Layers.UI)
            }

            render()
        }
    }

    override fun dispose() {
        check(!_isDisposed)

        if (::loadingJob.isInitialized)
            loadingJob.cancel()
        if (::changingStateJob.isInitialized)
            changingStateJob.cancel()

        _isDisposed = true
    }

    private fun updateLoadingText(progress: Float) {
        text = Text.create(font = Fonts.MONOSPACED_16, color = Colors.WHITE) {
            +"Now Loading..."
            block(color = Colors.BLACK, backColor = Colors.WHITE) {
                +(String.format("%.2f", progress * 100) + "%")
            }
        }
    }
}