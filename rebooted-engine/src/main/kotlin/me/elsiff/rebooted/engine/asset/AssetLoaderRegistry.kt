package me.elsiff.rebooted.engine.asset

import kotlin.reflect.KClass

/**
 * Created by elsiff on 2019-07-13.
 */
class AssetLoaderRegistry {
    private val loaders: MutableMap<KClass<out AssetLoadRequest>, AssetLoader> = mutableMapOf()

    fun register(clazz: KClass<out AssetLoadRequest>, drawer: AssetLoader) {
        loaders[clazz] = drawer
    }

    fun unregister(clazz: KClass<out AssetLoadRequest>) {
        loaders.remove(clazz)
    }

    fun loaderFor(clazz: KClass<out AssetLoadRequest>): AssetLoader {
        require(loaders.contains(clazz)) { "No loader for $clazz has been found" }
        return loaders[clazz]!!
    }
}