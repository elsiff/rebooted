package me.elsiff.rebooted.engine.event

/**
 * Created by elsiff on 2019-07-23.
 */
class EventTrigger(
    private val handlers: EventHandlerRegistry
) {
    fun call(event: Event) {
        for (handler in handlers[event::class]) {
            if (!handler.ignoreCancelled || !(event is Cancellable && event.hasCancelled)) {
                handler.handle(event)
            }
        }
    }
}