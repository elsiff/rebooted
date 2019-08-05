package me.elsiff.rebooted.game.world

import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.math.clamp
import me.elsiff.rebooted.engine.math.vec3f

/**
 * Created by elsiff on 2019-07-25.
 */
class Camera(
    private val world: World,
    private val size: Size
) {
    private var _position: Vector2f = minPos
    var position: Vector2f
        get() = _position
        set(value) {
            val clamped = clamp(value, minPos, maxPos)

            world.audioPlayer.listener.position = vec3f(clamped.x, -3f, clamped.y)
            _position = clamped
        }

    val origin: Vector2f
        get() = position - minPos

    private val minPos: Vector2f get() = this.size.toVector2f() / 2f
    private val maxPos: Vector2f get() = world.size.toVector2f() - minPos
}