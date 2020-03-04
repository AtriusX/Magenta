package io.atrius

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

typealias Token = String

fun main(args: Array<String>) {
    val api = if (args.isNotEmpty())
        login(args[0])
    else
        error("Argument mismatch: [Token]")

    api.addEventListener(Example())
}

fun login(token: Token, settings: JDABuilder.() -> Unit = {}): JDA =
    JDABuilder(token).apply(settings).build()


class Example : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) = println(event.message.contentDisplay)
}