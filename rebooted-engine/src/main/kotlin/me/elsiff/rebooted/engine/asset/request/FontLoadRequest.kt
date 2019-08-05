package me.elsiff.rebooted.engine.asset.request

import me.elsiff.rebooted.engine.asset.AssetLoadRequest

/**
 * Created by elsiff on 2019-07-15.
 */
class FontLoadRequest(
    val filePath: String,
    val fontSize: Float,
    val antiAlias: Boolean
) : AssetLoadRequest