package me.elsiff.rebooted.engine.lwjgl.util

import org.apache.commons.io.IOUtils
import java.io.IOException
import java.nio.charset.Charset

/**
 * Created by elsiff on 2019-03-04.
 */
object ResourceReader {
    fun readContents(resourceName: String): String {
        val classLoader = javaClass.classLoader
        val resource = classLoader.getResourceAsStream(resourceName)
            ?: throw IllegalArgumentException("$resourceName resource couldn't be found")

        try {
            return IOUtils.toString(resource, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            throw IllegalStateException("Couldn't read the resource $resourceName", e)
        }
    }
}