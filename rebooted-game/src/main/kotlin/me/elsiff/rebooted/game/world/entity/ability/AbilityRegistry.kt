package me.elsiff.rebooted.game.world.entity.ability

import me.elsiff.rebooted.engine.Disposable
import kotlin.reflect.KClass

/**
 * Created by elsiff on 2019-07-26.
 */
class AbilityRegistry : Disposable {
    private var _isDisabled = false
    override val isDisposed: Boolean get() = _isDisabled

    private val map: MutableMap<KClass<out Ability>, Ability> = mutableMapOf()

    fun register(ability: Ability) {
        require(!map.containsKey(ability::class)) { "Ability must not be registered yet" }

        map[ability::class] = ability
        ability.enable()
    }

    fun unregister(type: KClass<out Ability>) {
        val ability = map.remove(type) ?: error("Ability must be registered yet")
        ability.disable()
    }

    operator fun get(type: KClass<out Ability>): Ability? {
        return map[type]
    }

    override fun dispose() {
        check(!_isDisabled)

        for (key in map.keys.toList()) {
            unregister(key)
        }

        _isDisabled = true
    }
}