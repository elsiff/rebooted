package me.elsiff.rebooted.engine.lwjgl.asset

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.elsiff.rebooted.engine.asset.AssetLoadRequest
import me.elsiff.rebooted.engine.asset.AssetLoader
import me.elsiff.rebooted.engine.asset.request.TextureLoadRequest
import me.elsiff.rebooted.engine.lwjgl.LwjglEngine
import me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable.GlTexture
import me.elsiff.rebooted.engine.math.size
import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack

/**
 * Created by elsiff on 2019-03-13.
 */
class GlTextureLoader(
    private val engine: LwjglEngine
) : AssetLoader {
    override suspend fun load(request: AssetLoadRequest): GlTexture {
        require(request is TextureLoadRequest)

        val path = request.filePath

        MemoryStack.stackPush().use { stack ->
            val w = stack.mallocInt(1)
            val h = stack.mallocInt(1)
            val comp = stack.mallocInt(1)

            val image = withContext(Dispatchers.IO) {
                stbi_set_flip_vertically_on_load(true)
                stbi_load(path, w, h, comp, 4) ?: error(
                    "Failed to load a texture file $path (${stbi_failure_reason()})"
                )
            }

            return GlTexture(
                size = size(w.get(), h.get()),
                data = image
            )
        }
    }
}