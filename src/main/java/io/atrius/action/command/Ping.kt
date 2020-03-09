package io.atrius.action.command

import io.atrius.action.Command
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

object Ping : Command("ping") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {

        if (!user.isBot) channel.sendMessage(
                EmbedBuilder().appendDescription("Pong!").build()
        ).submit(true)
    }
}