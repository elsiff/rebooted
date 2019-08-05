package me.elsiff.rebooted.engine.lwjgl.window.graphic.batch

import me.elsiff.rebooted.engine.math.Matrix4f
import me.elsiff.rebooted.engine.window.Window
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL20.GL_VERTEX_SHADER


/**
 * Created by elsiff on 2019-03-04.
 */
class ShapeShaderProgram(
    vertexShaderSource: String,
    fragShaderSource: String,
    window: Window
) : ShaderProgram(window) {
    override val attributeAmount = 6

    init {
        val shapeVertShader = Shader(GL_VERTEX_SHADER)
        shapeVertShader.source(vertexShaderSource)
        shapeVertShader.compile()
        val shapeFragShader = Shader(GL_FRAGMENT_SHADER)
        shapeFragShader.source(fragShaderSource)
        shapeFragShader.compile()

        this.attachShader(shapeVertShader)
        this.attachShader(shapeFragShader)
        this.bindFragmentDataLocation(0, "fragColor")
        this.link()

        shapeVertShader.delete()
        shapeFragShader.delete()
    }

    override fun setUniformValues(windowWidth: Float, windowHeight: Float) {
        val model = Matrix4f()
        val uniModel = this.getUniformLocation("model")
        this.setUniform(uniModel, model)

        val view = Matrix4f()
        val uniView = this.getUniformLocation("view")
        this.setUniform(uniView, view)

        val projection = Matrix4f.orthographic(0f, windowWidth, 0f, windowHeight, -1f, 1f)
        val uniProjection = this.getUniformLocation("projection")
        this.setUniform(uniProjection, projection)
    }

    override fun specifyVertexAttributes() {
        val posAttribShape = this.getAttributeLocation("position")
        this.enableVertexAttribute(posAttribShape)
        this.pointVertexAttribute(posAttribShape, 2, GL_FLOAT, 6 * java.lang.Float.BYTES, 0)

        val colAttribShape = this.getAttributeLocation("color")
        this.enableVertexAttribute(colAttribShape)
        this.pointVertexAttribute(colAttribShape, 4, GL_FLOAT, 6 * java.lang.Float.BYTES, 2L * java.lang.Float.BYTES)
    }
}