package me.elsiff.rebooted.game.world

/**
 * Created by elsiff on 2019-08-21.
 */
object AltitudeRanges {
    val EMPTY: IntRange = IntRange.EMPTY
    val GROUND_ONLY: IntRange = Altitudes.GROUND..Altitudes.GROUND
    val LOW_SKY_ONLY: IntRange = Altitudes.LOW_SKY..Altitudes.LOW_SKY
    val HIGH_SKY_ONLY: IntRange = Altitudes.HIGH_SKY..Altitudes.HIGH_SKY
}