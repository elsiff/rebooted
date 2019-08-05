package me.elsiff.rebooted.engine.event

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.valueParameters

/**
 * Created by elsiff on 2019-07-23.
 */
class EventHandlerFunction<R>(
    private val functionOwner: EventListener,
    function: KFunction<R>
) : KFunction<R> by function {
    private val handlerAnnotation: EventHandler =
        function.findAnnotation() ?: error("Function must have an EventHandler annotation")

    val handlingOrder = handlerAnnotation.handlingNumber
    val ignoreCancelled = handlerAnnotation.ignoreCancelled
    val eventType: KClass<out Event>

    init {
        @Suppress("UNCHECKED_CAST")
        eventType = function.valueParameters.first().type.classifier as KClass<out Event>
    }

    fun handle(event: Event) = call(functionOwner, event)
}