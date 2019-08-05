package me.elsiff.rebooted.engine.window.graphic.drawable

import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.math.Vector2f

/**
 * Created by elsiff on 2019-03-06.
 */
interface TextureRegion : Drawable {
    val atlas: Texture
    val coordinate: Vector2f
    val size: Size
}