package me.elsiff.rebooted.game.event

import me.elsiff.rebooted.engine.event.Event
import me.elsiff.rebooted.game.world.World

/**
 * Created by elsiff on 2019-08-01.
 */
class WorldUpdateEvent(
    val world: World,
    val deltaTime: Float
) : Event