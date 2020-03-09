package io.atrius.manager

import io.atrius.Listener

object Events {

    fun register(vararg events: Listener) =
            Bot.api.addEventListener(events)

    fun unregister(vararg events: Listener) =
            Bot.api.removeEventListener(events)
}