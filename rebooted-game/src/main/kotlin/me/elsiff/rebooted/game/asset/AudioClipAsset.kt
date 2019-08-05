package me.elsiff.rebooted.game.asset

/**
 * Created by elsiff on 2019-07-24.
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Asset(AssetType.AUDIO_CLIP)
annotation class AudioClipAsset(
    val filePath: String
)