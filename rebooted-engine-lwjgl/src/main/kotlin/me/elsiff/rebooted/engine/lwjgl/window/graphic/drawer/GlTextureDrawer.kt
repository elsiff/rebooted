package me.elsiff.rebooted.engine.lwjgl.window.graphic.drawer

import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.Batch
import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.ShaderProgramType
import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.Vertex
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable.GlTexture
import me.elsiff.rebooted.engine.math.vec2f
import me.elsiff.rebooted.engine.window.graphic.Color
import me.elsiff.rebooted.engine.window.graphic.drawable.Drawable

/**
 * Created by elsiff on 2019-03-06.
 */
class GlTextureDrawer : Drawer {
    override fun draw(thing: Drawable, batch: Batch, option: Drawable.DrawingOption) {
        require(thing is GlTexture)

        val position = option.position
        val v2 = position + vec2f(thing.size.width, thing.size.height)

        val x1 = position.x
        val y1 = position.y
        val x2 = v2.x
        val y2 = v2.y
        val r = BASE_COLOR.red
        val g = BASE_COLOR.green
        val b = BASE_COLOR.blue
        val a = BASE_COLOR.alpha
        val s1 = 0f
        val t1 = 0f
        val s2 = 1f
        val t2 = 1f

        thing.bind()
        thing.uploadData()

        batch.putVertices(
            ShaderProgramType.TEXTURE,

            Vertex.of(x1, y1, r, g, b, a, s1, t1),
            Vertex.of(x1, y2, r, g, b, a, s1, t2),
            Vertex.of(x2, y2, r, g, b, a, s2, t2),

            Vertex.of(x1, y1, r, g, b, a, s1, t1),
            Vertex.of(x2, y2, r, g, b, a, s2, t2),
            Vertex.of(x2, y1, r, g, b, a, s2, t1)
        )

        batch.flush()
    }

    companion object {
        private val BASE_COLOR: Color = Color.WHITE
    }
}