package me.elsiff.rebooted.engine.lwjgl.audio

import org.lwjgl.openal.AL10.alBufferData
import org.lwjgl.openal.AL10.alGenBuffers
import java.nio.ShortBuffer

/**
 * Created by elsiff on 2019-07-24.
 */
class AlBuffer {
    val id = alGenBuffers()

    fun uploadData(format: Int, rawAudioBuffer: ShortBuffer, sampleRate: Int) {
        alBufferData(id, format, rawAudioBuffer, sampleRate)
    }
}