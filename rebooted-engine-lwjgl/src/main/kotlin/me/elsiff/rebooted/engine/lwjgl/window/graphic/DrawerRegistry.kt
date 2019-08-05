package me.elsiff.rebooted.engine.lwjgl.window.graphic

import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawer.Drawer
import me.elsiff.rebooted.engine.window.graphic.drawable.Drawable
import kotlin.reflect.KClass

/**
 * Created by elsiff on 2019-07-13.
 */
class DrawerRegistry {
    private val drawers: MutableMap<KClass<out Drawable>, Drawer> = mutableMapOf()

    fun register(clazz: KClass<out Drawable>, drawer: Drawer) {
        drawers[clazz] = drawer
    }

    fun unregister(clazz: KClass<out Drawable>) {
        drawers.remove(clazz)
    }

    operator fun get(clazz: KClass<out Drawable>): Drawer {
        val drawer = drawers[clazz]
        require(drawer != null) { "No drawer for $clazz has been found" }
        return drawer
    }
}