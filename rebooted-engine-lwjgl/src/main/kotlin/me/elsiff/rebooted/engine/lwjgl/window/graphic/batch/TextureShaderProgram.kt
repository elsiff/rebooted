package me.elsiff.rebooted.engine.lwjgl.window.graphic.batch

import me.elsiff.rebooted.engine.math.Matrix4x4f
import me.elsiff.rebooted.engine.window.Window
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL20.GL_VERTEX_SHADER

/**
 * Created by elsiff on 2019-03-04.
 */
class TextureShaderProgram(
    vertexShaderSource: String,
    fragShaderSource: String,
    window: Window
) : ShaderProgram(window) {
    override val attributeAmount = 8

    init {
        val textureVertShader = Shader(GL_VERTEX_SHADER)
        textureVertShader.source(vertexShaderSource)
        textureVertShader.compile()
        val textureFragShader = Shader(GL_FRAGMENT_SHADER)
        textureFragShader.source(fragShaderSource)
        textureFragShader.compile()

        this.attachShader(textureVertShader)
        this.attachShader(textureFragShader)
        this.bindFragmentDataLocation(0, "fragColor")
        this.link()

        textureVertShader.delete()
        textureFragShader.delete()
    }

    override fun setUniformValues(windowWidth: Float, windowHeight: Float) {
        val uniTex = this.getUniformLocation("texImage")
        this.setUniform(uniTex, 0)

        val model = Matrix4x4f.IDENTITY
        val uniModel = this.getUniformLocation("model")
        this.setUniform(uniModel, model)

        val view = Matrix4x4f.IDENTITY
        val uniView = this.getUniformLocation("view")
        this.setUniform(uniView, view)

        val projection = Matrix4x4f.orthographic(
            left = 0f, right = windowWidth,
            bottom = 0f, top = windowHeight,
            near = -1f, far = 1f
        )
        val uniProjection = this.getUniformLocation("projection")
        this.setUniform(uniProjection, projection)
    }

    override fun specifyVertexAttributes() {
        val posAttrib = this.getAttributeLocation("position")
        this.enableVertexAttribute(posAttrib)
        this.pointVertexAttribute(posAttrib, 2, GL_FLOAT, 8 * java.lang.Float.BYTES, 0)

        val colAttrib = this.getAttributeLocation("color")
        this.enableVertexAttribute(colAttrib)
        this.pointVertexAttribute(colAttrib, 4, GL_FLOAT, 8 * java.lang.Float.BYTES, 2L * java.lang.Float.BYTES)

        val texAttrib = this.getAttributeLocation("texcoord")
        this.enableVertexAttribute(texAttrib)
        this.pointVertexAttribute(texAttrib, 2, GL_FLOAT, 8 * java.lang.Float.BYTES, 6L * java.lang.Float.BYTES)
    }
}