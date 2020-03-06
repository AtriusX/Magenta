package io.atrius

import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

fun main(args: Array<String>) = Bot.start(args) {
    CommandManager.register(Example, Status)
}

object Example : Command("ping") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        if (!user.isBot) channel.sendMessage("Pong!").submit(true)
    }
}

object Status : Command("status") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        if (args.isNotEmpty())
            Bot.api.presence.setPresence(Activity.playing(args.joinToString(" ")), false)
    }
}