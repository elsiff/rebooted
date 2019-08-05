package me.elsiff.rebooted.engine.event

import java.util.*
import kotlin.reflect.KClass

/**
 * Created by elsiff on 2019-07-23.
 */
class EventHandlerRegistry {
    private val handlers: MutableMap<KClass<out Event>, MutableList<EventHandlerFunction<*>>> = mutableMapOf()

    fun register(listener: EventListener) {
        for (function in listener.handlerFunctions()) {
            val functions = handlers.getOrPut(function.eventType) { mutableListOf() }

            functions += function
            functions.sortWith(HANDLER_COMPARATOR)
        }
    }

    fun unregister(listener: EventListener) {
        for (function in listener.handlerFunctions()) {
            if (handlers.containsKey(function.eventType)) {
                handlers[function.eventType]!! -= function
            }
        }
    }

    operator fun get(eventType: KClass<out Event>): Collection<EventHandlerFunction<*>> {
        return handlers[eventType] ?: emptySet()
    }

    companion object {
        private val HANDLER_COMPARATOR: Comparator<EventHandlerFunction<*>> = compareBy { it.handlingOrder }
    }
}