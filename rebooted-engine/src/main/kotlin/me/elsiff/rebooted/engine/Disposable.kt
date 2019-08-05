package me.elsiff.rebooted.engine

/**
 * Created by elsiff on 2019-02-26.
 */
interface Disposable {
    val isDisposed: Boolean

    fun dispose()
}