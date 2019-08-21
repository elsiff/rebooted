package me.elsiff.rebooted.game.world.entity.rule

import me.elsiff.rebooted.game.GameRule
import me.elsiff.rebooted.game.world.entity.Entity

/**
 * Created by elsiff on 2019-08-21.
 */
interface EntityRule : GameRule {
    val entity: Entity
}