package me.elsiff.rebooted.game.world.entity

import me.elsiff.rebooted.engine.math.Vector2f
import me.elsiff.rebooted.engine.math.size
import me.elsiff.rebooted.game.graphic.BulletGraphic
import me.elsiff.rebooted.game.graphic.Graphic
import me.elsiff.rebooted.game.world.AltitudeRanges
import me.elsiff.rebooted.game.world.Altitudes
import me.elsiff.rebooted.game.world.World
import me.elsiff.rebooted.game.world.entity.ability.AbilityRegistry
import me.elsiff.rebooted.game.world.physic.AABB
import me.elsiff.rebooted.game.world.physic.RigidBody

/**
 * Created by elsiff on 2019-08-01.
 */
class Bullet(
    override val world: World,
    position: Vector2f
) : Entity {
    private var _isDisposed = false
    override val isDisposed: Boolean get() = _isDisposed

    override val rigidBody: RigidBody = RigidBody(
        position = position,
        mass = 1f,
        restitution = 1f,
        drag = 0.25f
    ).apply {
        boundings += AABB(this, size(10f, 10f))
    }
    override val altitude: Int = Altitudes.LOW_SKY
    override val blockingAltitudes: IntRange = AltitudeRanges.EMPTY

    override val graphic: Graphic = BulletGraphic()
    override val abilities: AbilityRegistry = AbilityRegistry()

    override fun handleCollided(other: Entity) {
        // Do Nothing
    }

    override fun dispose() {
        check(!_isDisposed)

        abilities.dispose()

        _isDisposed = true
    }
}