package me.elsiff.rebooted.game.ui.widget

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.game.graphic.Graphic

/**
 * Created by elsiff on 2019-07-27.
 */
interface Widget : Disposable {
    val graphic: Graphic
    val position: Vector2f
}