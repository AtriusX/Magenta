package io.atrius.manager

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener

object Events : EventListener {

    private val events = arrayListOf<EventListener>()

    override fun onEvent(event: GenericEvent) = events.forEach {
        it.onEvent(event)
    }

    fun register(vararg events: EventListener) =
            this.events.addAll(events)

    fun unregister(vararg events: EventListener) =
            this.events.removeAll(events)
}