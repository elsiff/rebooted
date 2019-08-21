package me.elsiff.rebooted.game.world.rule

import me.elsiff.rebooted.game.GameRule
import me.elsiff.rebooted.game.world.World

/**
 * Created by elsiff on 2019-08-21.
 */
interface WorldRule : GameRule {
    val world: World
}