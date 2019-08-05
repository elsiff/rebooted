package me.elsiff.rebooted.engine.event

import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.findAnnotation

/**
 * Created by elsiff on 2019-07-22.
 */
interface EventListener {
    fun handlerFunctions(): Collection<EventHandlerFunction<*>> =
        this::class.declaredFunctions
            .filter { it.findAnnotation<EventHandler>() != null }
            .map { EventHandlerFunction(this, it) }
}