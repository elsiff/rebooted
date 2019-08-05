package me.elsiff.rebooted.game.asset

/**
 * Created by elsiff on 2019-07-04.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Asset(
    val type: AssetType
)