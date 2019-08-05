package me.elsiff.rebooted.engine.asset

/**
 * Created by elsiff on 2019-03-13.
 */
interface AssetLoader {
    suspend fun load(request: AssetLoadRequest): Any
}