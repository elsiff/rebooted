package me.elsiff.rebooted.engine.lwjgl.window.graphic.drawer

import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.Batch
import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.ShaderProgramType
import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.Vertex
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable.GlTextureRegion
import me.elsiff.rebooted.engine.window.graphic.Color
import me.elsiff.rebooted.engine.window.graphic.drawable.Drawable

/**
 * Created by elsiff on 2019-03-06.
 */
class GlTextureRegionDrawer : Drawer {
    override fun draw(thing: Drawable, batch: Batch, option: Drawable.DrawingOption) {
        require(thing is GlTextureRegion)

        val position = option.position
        val v2 = position.plus(thing.size.width, thing.size.height)
        val color = if (option is GlTextureRegion.DrawingOption) option.color else DEFAULT_COLOR

        val x1 = position.x
        val y1 = position.y
        val x2 = v2.x
        val y2 = v2.y
        val r = color.red
        val g = color.green
        val b = color.blue
        val a = color.alpha
        val s1 = thing.coordinate.x / thing.atlas.size.width
        val t1 = thing.coordinate.y / thing.atlas.size.height
        val s2 = (thing.coordinate.x + thing.size.width) / thing.atlas.size.width
        val t2 = (thing.coordinate.y + thing.size.height) / thing.atlas.size.height

        thing.atlas.bind()
        thing.atlas.uploadData()

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
        private val DEFAULT_COLOR = Color.WHITE
    }
}