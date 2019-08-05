package me.elsiff.rebooted.engine.lwjgl.asset

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.elsiff.rebooted.engine.asset.AssetLoadRequest
import me.elsiff.rebooted.engine.asset.AssetLoader
import me.elsiff.rebooted.engine.asset.request.AudioClipLoadRequest
import me.elsiff.rebooted.engine.lwjgl.LwjglEngine
import me.elsiff.rebooted.engine.lwjgl.audio.AlAudioClip
import me.elsiff.rebooted.engine.lwjgl.audio.AlBuffer
import org.lwjgl.openal.AL10.AL_FORMAT_MONO16
import org.lwjgl.openal.AL10.AL_FORMAT_STEREO16
import org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename
import org.lwjgl.system.MemoryStack.*
import org.lwjgl.system.libc.LibCStdlib.free


/**
 * Created by elsiff on 2019-07-24.
 */
class AlAudioClipLoader(
    private val engine: LwjglEngine
) : AssetLoader {
    override suspend fun load(request: AssetLoadRequest): AlAudioClip {
        require(request is AudioClipLoadRequest)

        val path = request.filePath

        stackPush()
        val channelsBuffer = stackMallocInt(1)
        stackPush()
        val sampleRateBuffer = stackMallocInt(1)

        val rawAudioBuffer = withContext(Dispatchers.IO) {
            stb_vorbis_decode_filename(path, channelsBuffer, sampleRateBuffer)
                ?: error("Failed to load a texture file $path")
        }

        val channels = channelsBuffer.get()
        val sampleRate = sampleRateBuffer.get()

        stackPop()
        stackPop()

        val format = when (channels) {
            1 -> AL_FORMAT_MONO16
            2 -> AL_FORMAT_STEREO16
            else -> -1
        }

        val buffer = AlBuffer()
        buffer.uploadData(format, rawAudioBuffer, sampleRate)

        free(rawAudioBuffer)
        return AlAudioClip(buffer)
    }
}