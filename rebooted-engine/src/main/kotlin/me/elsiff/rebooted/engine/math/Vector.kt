package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-08-05.
 */
interface Vector<T : Number> : Iterable<T> {
    val elements: List<T>
    
    val length: T
    val lengthSquared: T

    fun normalized(): Vector<T>

    operator fun unaryPlus(): Vector<T>
    operator fun unaryMinus(): Vector<T>
    operator fun plus(increment: T): Vector<T>
    operator fun minus(decrement: T): Vector<T>
    operator fun times(multiplier: T): Vector<T>
    operator fun div(divider: T): Vector<T>

    override fun iterator(): Iterator<T> = elements.iterator()
}