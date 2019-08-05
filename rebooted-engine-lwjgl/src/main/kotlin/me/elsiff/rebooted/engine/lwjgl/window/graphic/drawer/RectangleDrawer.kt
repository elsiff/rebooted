package me.elsiff.rebooted.engine.lwjgl.window.graphic.drawer

import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.Batch
import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.ShaderProgramType
import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.Vertex
import me.elsiff.rebooted.engine.window.graphic.drawable.Drawable
import me.elsiff.rebooted.engine.window.graphic.drawable.Rectangle

/**
 * Created by elsiff on 2019-02-28.
 */
class RectangleDrawer : Drawer {
    override fun draw(thing: Drawable, batch: Batch, option: Drawable.DrawingOption) {
        require(thing is Rectangle)

        val position = option.position
        val x1 = position.x
        val y1 = position.y
        val x2 = x1 + thing.size.width
        val y2 = y1 + thing.size.height
        val r = thing.color.red
        val g = thing.color.green
        val b = thing.color.blue
        val a = thing.color.alpha

        batch.putVertices(
            ShaderProgramType.SHAPE,

            Vertex.of(x1, y1, r, g, b, a),
            Vertex.of(x1, y2, r, g, b, a),
            Vertex.of(x2, y2, r, g, b, a),

            Vertex.of(x1, y1, r, g, b, a),
            Vertex.of(x2, y2, r, g, b, a),
            Vertex.of(x2, y1, r, g, b, a)
        )
    }
}