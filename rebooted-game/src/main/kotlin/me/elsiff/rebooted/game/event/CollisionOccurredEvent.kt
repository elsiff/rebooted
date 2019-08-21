package me.elsiff.rebooted.game.event

import me.elsiff.rebooted.engine.event.Event
import me.elsiff.rebooted.game.world.entity.Entity
import me.elsiff.rebooted.game.world.physic.collision.Collision

/**
 * Created by elsiff on 2019-08-21.
 */
class CollisionOccurredEvent(
    val collision: Collision,
    val entities: Set<Entity>
) : Event