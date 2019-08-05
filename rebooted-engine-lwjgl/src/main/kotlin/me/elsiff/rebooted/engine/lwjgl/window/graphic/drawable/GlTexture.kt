package me.elsiff.rebooted.engine.lwjgl.window.graphic.drawable

import me.elsiff.rebooted.engine.math.Size
import me.elsiff.rebooted.engine.math.size
import me.elsiff.rebooted.engine.window.graphic.drawable.Texture
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13
import org.lwjgl.system.MemoryUtil
import java.awt.image.BufferedImage
import java.nio.ByteBuffer


/**
 * Created by elsiff on 2019-02-28.
 */
class GlTexture(
    override val size: Size,
    private val data: ByteBuffer
) : Texture {
    private val id = glGenTextures()

    fun bind() {
        glBindTexture(GL_TEXTURE_2D, id)
    }

    fun uploadData() {
        this.setParameter(GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER)
        this.setParameter(GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER)
        this.setParameter(GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
        this.setParameter(GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)

        glTexImage2D(
            GL_TEXTURE_2D,
            0, GL_RGBA8,
            size.width.toInt(), size.height.toInt(),
            0, GL_RGBA, GL_UNSIGNED_BYTE, data
        )
    }

    private fun setParameter(name: Int, value: Int) {
        glTexParameteri(GL_TEXTURE_2D, name, value)
    }

    fun delete() {
        glDeleteTextures(id)
    }

    companion object {
        fun createFrom(image: BufferedImage): GlTexture {
            /* Get charWidth and charHeight of image */
            val width = image.width
            val height = image.height

            /* Get pixel data of image */
            val pixels = IntArray(width * height)
            image.getRGB(0, 0, width, height, pixels, 0, width)

            /* Put pixel data into a ByteBuffer */
            val buffer = MemoryUtil.memAlloc(width * height * 4)
            for (i in 0 until height) {
                for (j in 0 until width) {
                    /* Pixel as RGBA: 0xAARRGGBB */
                    val pixel = pixels[i * width + j]
                    /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
                    buffer.put((pixel shr 16 and 0xFF).toByte())
                    /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
                    buffer.put((pixel shr 8 and 0xFF).toByte())
                    /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
                    buffer.put((pixel and 0xFF).toByte())
                    /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
                    buffer.put((pixel shr 24 and 0xFF).toByte())
                }
            }
            buffer.flip()

            return GlTexture(size(width, height), buffer)
        }
    }
}