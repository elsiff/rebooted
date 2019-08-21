package me.elsiff.rebooted.game

import me.elsiff.rebooted.engine.Disposable

/**
 * Created by elsiff on 2019-08-21.
 */
typealias Condition = () -> Boolean

interface GameRule : Disposable {
    interface Conditional {
        val conditions: Collection<Condition>
        val isQualified: Boolean
            get() = conditions.all { it() }
    }
}