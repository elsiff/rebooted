package me.elsiff.rebooted.game.asset

/**
 * Created by elsiff on 2019-07-04.
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Asset(AssetType.FONT)
annotation class FontAsset(
    val filePath: String,
    val fontSize: Float,
    val antiAlias: Boolean
)