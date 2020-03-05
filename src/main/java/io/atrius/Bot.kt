package io.atrius

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

typealias Token =
        String

fun main(args: Array<String>) {
    val api = if (args.isNotEmpty())
        login(args[0])
    else
        error("Argument mismatch: [Token]")

    api.addEventListener(example)
}

fun login(token: Token, settings: JDABuilder.() -> Unit = {}): JDA =
    JDABuilder(token).apply(settings).build()


val example = object : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (!event.message.author.isBot)
            event.channel
                    .sendMessage(event.message.contentDisplay)
                    .submit(true)
    }
}

open class Command(
    private val command    : String,
    private val description: String      = "Default description for $command",
    private val type       : CommandType = CommandType.ANY
) : ListenerAdapter() {

    final override fun onMessageReceived(event: MessageReceivedEvent) {
        // Skip over commands that don't fit the right channel type
        if (!event.channelType.isGuild && type == CommandType.PRIVATE) {
            return
        }
    }
}

enum class CommandType {
    PRIVATE, GUILD, ANY
}