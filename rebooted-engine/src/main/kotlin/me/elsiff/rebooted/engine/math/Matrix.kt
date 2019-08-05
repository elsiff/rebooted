package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-08-06.
 */
interface Matrix<T : Number> : Iterable<T> {
    val elements: List<T>

    fun transposed(): Matrix<T>

    operator fun unaryPlus(): Matrix<T>
    operator fun unaryMinus(): Matrix<T>
    operator fun plus(increment: T): Matrix<T>
    operator fun minus(decrement: T): Matrix<T>
    operator fun times(multiplier: T): Matrix<T>
    operator fun div(divider: T): Matrix<T>

    override fun iterator(): Iterator<T> = elements.iterator()
}