package me.elsiff.rebooted.engine.lwjgl.window.graphic

import me.elsiff.rebooted.engine.lwjgl.window.GlfwWindow
import me.elsiff.rebooted.engine.lwjgl.window.graphic.batch.Batch
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable.GlTexture
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable.GlTextureRegion
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawer.GlTextureDrawer
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawer.GlTextureRegionDrawer
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawer.RectangleDrawer
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawer.TextDrawer
import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.window.graphic.Color
import me.elsiff.rebooted.engine.window.graphic.Graphics
import me.elsiff.rebooted.engine.window.graphic.drawable.Drawable
import me.elsiff.rebooted.engine.window.graphic.drawable.Rectangle
import me.elsiff.rebooted.engine.window.graphic.drawable.Text
import java.util.*

/**
 * Created by elsiff on 2019-03-14.
 */
class GlfwGraphics(
    override val window: GlfwWindow
) : Graphics {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    private val batch: Batch = Batch(window)
    private val drawings: MutableList<DrawingWithOption> = mutableListOf()

    private val drawerRegistry: DrawerRegistry = DrawerRegistry()
        .apply {
            val rectangleDrawer = RectangleDrawer()
            val glTextureRegionDrawer = GlTextureRegionDrawer()

            register(Rectangle::class, rectangleDrawer)
            register(
                GlTexture::class,
                GlTextureDrawer()
            )
            register(GlTextureRegion::class, glTextureRegionDrawer)
            register(
                Text::class,
                TextDrawer(glTextureRegionDrawer, rectangleDrawer)
            )
        }

    private fun draw(drawing: Drawable, option: Drawable.DrawingOption) {
        drawerRegistry[drawing::class].draw(drawing, batch, option)
    }

    override fun clear() {
        drawings.clear()
        batch.clear(Color.BLACK)
    }

    override fun add(drawing: Drawable, position: Vector2f, drawingNumber: Int) {
        val option = Drawable.DrawingOption(position, drawingNumber)
        drawings.add(DrawingWithOption(drawing, option))
        drawings.sortWith(DRAWING_COMPARATOR)
    }

    override fun render() {
        batch.begin()

        for ((drawing, option) in drawings) {
            draw(drawing, option)
        }

        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }

    companion object {
        private val DRAWING_COMPARATOR: Comparator<DrawingWithOption> = compareBy { it.option.drawingNumber }
    }
}

class DrawingWithOption(
    val drawable: Drawable,
    val option: Drawable.DrawingOption
) {
    operator fun component1(): Drawable = drawable
    operator fun component2(): Drawable.DrawingOption = option
}