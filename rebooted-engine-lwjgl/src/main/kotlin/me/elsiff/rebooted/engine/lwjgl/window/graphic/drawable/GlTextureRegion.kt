package me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable

import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.window.graphic.Color
import me.elsiff.rebooted.engine.window.graphic.drawable.Drawable
import me.elsiff.rebooted.engine.window.graphic.drawable.TextureRegion

/**
 * Created by elsiff on 2019-03-06.
 */
class GlTextureRegion(
    override val atlas: GlTexture,
    override val coordinate: Vector2f,
    override val size: Size
) : TextureRegion {
    class DrawingOption(
        position: Vector2f,
        drawingNumber: Int,
        val color: Color
    ) : Drawable.DrawingOption(position, drawingNumber)
}