package me.elsiff.rebooted.game.ui

import me.elsiff.rebooted.engine.Disposable
import me.elsiff.rebooted.game.ui.widget.Widget

/**
 * Created by elsiff on 2019-07-27.
 */
class Layout : Disposable {
    private var _isDisposed = false
    override val isDisposed: Boolean get() = _isDisposed

    private val _widgets: MutableList<Widget> = mutableListOf()
    val widgets: List<Widget> get() = _widgets

    fun addWidget(widget: Widget) {
        require(!_widgets.contains(widget))
        _widgets += widget
    }

    operator fun plusAssign(widget: Widget) = addWidget(widget)

    fun removeWidget(widget: Widget) {
        require(_widgets.contains(widget))
        _widgets -= widget
    }

    operator fun minusAssign(widget: Widget) = removeWidget(widget)

    override fun dispose() {
        check(!_isDisposed)

        for (widget in _widgets) {
            widget.dispose()
        }
        _widgets.clear()

        _isDisposed = true
    }
}