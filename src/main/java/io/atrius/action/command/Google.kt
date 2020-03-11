package io.atrius.action.command

import io.atrius.action.Command
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

object Google : Command("google") {

    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        if (args.isNotEmpty())
            channel.sendMessage("https://google.com/search?q=${args.joinToString("+")}").submit()
    }
}