package me.elsiff.rebooted.engine.window

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.Engine
import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.window.graphic.Graphics
import me.elsiff.rebooted.engine.window.input.Input

/**
 * Created by elsiff on 2019-02-26.
 */
interface Window : Disposable {
    var title: String
    var size: Size
    var shouldClose: Boolean
    var isHidingCursor: Boolean
    val engine: Engine
    val graphics: Graphics
    val input: Input

    fun update()

    fun resize(width: Int, height: Int)

    fun makeContextCurrent()
}