package io.atrius.manager

import io.atrius.action.Listener

object Events {

    fun register(vararg events: Listener) =
            Bot.api.addEventListener(events)

    fun unregister(vararg events: Listener) =
            Bot.api.removeEventListener(events)
}