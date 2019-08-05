package me.elsiff.rebooted.engine.lwjgl.window.graphic.batch

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.engine.lwjgl.util.ResourceReader
import me.elsiff.rebooted.engine.lwjgl.window.GlfwWindow

/**
 * Created by elsiff on 2019-03-04.
 */
class ShaderProgramSelection(
    window: GlfwWindow
) : Disposable {
    private var _isDisposed: Boolean = false
    override val isDisposed: Boolean get() = _isDisposed

    private val programMap: Map<ShaderProgramType, ShaderProgram>

    private var _currentType: ShaderProgramType = ShaderProgramType.NONE
    val currentType: ShaderProgramType get() = _currentType

    init {
        val shapeShaderProgram = ShapeShaderProgram(
            vertexShaderSource = ResourceReader.readContents("shape.vert"),
            fragShaderSource = ResourceReader.readContents("shape.frag"),
            window = window
        )
        val textureShaderProgram = TextureShaderProgram(
            vertexShaderSource = ResourceReader.readContents("texture.vert"),
            fragShaderSource = ResourceReader.readContents("texture.frag"),
            window = window
        )

        programMap = mapOf(
            ShaderProgramType.SHAPE to shapeShaderProgram,
            ShaderProgramType.TEXTURE to textureShaderProgram
        )
    }

    val current: ShaderProgram
        get() = programMap.getValue(_currentType)

    fun select(programType: ShaderProgramType) {
        if (_currentType != programType) {
            val program = programMap[programType] ?: throw IllegalStateException("No shader program couldn't be found")
            program.use()
            _currentType = programType
        }
    }

    override fun dispose() {
        check(!_isDisposed)
        for (program in programMap.values) {
            program.delete()
        }
        _isDisposed = true
    }
}