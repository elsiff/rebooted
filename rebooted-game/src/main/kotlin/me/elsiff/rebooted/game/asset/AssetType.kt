package me.elsiff.rebooted.game.asset

import me.elsiff.rebooted.engine.asset.AssetLoadRequest
import me.elsiff.rebooted.engine.asset.request.AudioClipLoadRequest
import me.elsiff.rebooted.engine.asset.request.FontLoadRequest
import me.elsiff.rebooted.engine.asset.request.TextureLoadRequest

/**
 * Created by elsiff on 2019-07-04.
 */
enum class AssetType(
    private val loadRequestFactory: (Annotation) -> AssetLoadRequest
) {
    TEXTURE({ require(it is TextureAsset); TextureLoadRequest(it.filePath) }),
    FONT({ require(it is FontAsset); FontLoadRequest(it.filePath, it.fontSize, it.antiAlias) }),
    AUDIO_CLIP({ require(it is AudioClipAsset); AudioClipLoadRequest(it.filePath) });

    fun createLoadRequest(annotation: Annotation): AssetLoadRequest {
        return loadRequestFactory(annotation)
    }
}