package me.elsiff.rebooted.game.asset

import me.elsiff.rebooted.engine.asset.AssetLoadRequest
import me.elsiff.rebooted.engine.asset.AssetLoaderRegistry
import me.elsiff.rebooted.game.Game
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.full.findAnnotation

/**
 * Created by elsiff on 2019-07-04.
 */
class AssetAssigner {
    private val loaders: AssetLoaderRegistry = Game.engine.assetLoaders

    suspend fun assignAssetTo(property: KMutableProperty<*>, propertyOwner: Any? = null) {
        val request = findAssetLoadRequestFrom(property)
            ?: throw IllegalArgumentException("Property '$property' is not a asset property")
        val asset = loaders.loaderFor(request::class).load(request)

        assign(asset, property, propertyOwner)
    }

    private fun findAssetLoadRequestFrom(property: KMutableProperty<*>): AssetLoadRequest? {
        for (annotation in property.annotations) {
            val assetMetaAnnotation = annotation.annotationClass.findAnnotation<Asset>() ?: continue
            val assetType = assetMetaAnnotation.type

            return assetType.createLoadRequest(annotation)
        }
        return null
    }

    private fun assign(asset: Any, property: KMutableProperty<*>, propertyOwner: Any?) {
        when (property) {
            is KMutableProperty0 -> property.setter.call(asset)
            else -> {
                require(propertyOwner != null) { "Owner instance is required for an unbound property" }
                property.setter.call(propertyOwner, asset)
            }
        }
    }
}