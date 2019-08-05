package me.elsiff.rebooted.engine.window.graphic.drawable

import me.elsiff.rebooted.engine.window.graphic.Color
import me.elsiff.rebooted.engine.window.graphic.Font

/**
 * Created by elsiff on 2019-02-28.
 */
class Text private constructor(
    val block: TextBlock
) : Drawable {
    interface Element {
        fun traverse(lambda: (String, Style) -> Unit)
    }

    class StringElement(
        val string: String,
        val style: Style
    ) : Element {
        override fun traverse(lambda: (String, Style) -> Unit) {
            lambda(string, style)
        }
    }

    @DslMarker
    annotation class TextBlockMarker

    @TextBlockMarker
    class TextBlock(
        private val style: Style
    ) : Element {
        private val _children: MutableList<Element> = arrayListOf()

        constructor(
            font: Font,
            color: Color,
            backColor: Color
        ) : this(Style(font, color, backColor))

        val children: List<Element>
            get() = _children

        fun block(
            font: Font = style.font,
            color: Color = style.color,
            backColor: Color = style.backColor,
            init: TextBlock.() -> Unit
        ) {
            val child = TextBlock(font, color, backColor)
            child.init()
            _children.add(child)
        }

        fun nextLine() {
            _children.add(
                StringElement(
                    NEXT_LINE.toString(),
                    style
                )
            )
        }

        operator fun String.unaryPlus() {
            _children.add(StringElement(this, style))
        }

        override fun traverse(lambda: (String, Style) -> Unit) {
            for (child in children) {
                child.traverse(lambda)
            }
        }
    }

    data class Style(
        val font: Font,
        val color: Color,
        val backColor: Color
    ) {
        companion object {
            val DEFAULT_BACK_COLOR = Color.TRANSPARENT
        }
    }

    companion object {
        val CHARS: List<Char> = (32.toChar()).rangeTo(255.toChar()).minus(127.toChar())
        const val NEXT_LINE = '\n'

        fun create(
            font: Font,
            color: Color,
            backColor: Color = Style.DEFAULT_BACK_COLOR,
            init: TextBlock.() -> Unit
        ): Text {
            val block = TextBlock(font, color, backColor)
            block.init()
            return Text(block)
        }
    }
}