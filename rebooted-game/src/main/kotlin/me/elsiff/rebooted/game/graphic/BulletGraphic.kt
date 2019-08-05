package me.elsiff.rebooted.game.graphic

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.window.graphic.drawable.Text
import me.elsiff.rebooted.game.Colors
import me.elsiff.rebooted.game.asset.Fonts

/**
 * Created by elsiff on 2019-08-01.
 */
class BulletGraphic : Graphic {
    private val _components: MutableList<Graphic.Component> = mutableListOf()
    override val components: Collection<Graphic.Component> get() = _components

    init {
        _components.add(
            Graphic.Component(
                drawing = Text.create(font = Fonts.MONOSPACED_16, color = Colors.LIGHT_GRAY) { +"*" },
                offset = Vector2f.ZERO,
                layer = Layers.PLAYER
            )
        )
    }
}