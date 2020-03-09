package io.atrius.action.command

import io.atrius.action.Command
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

object Egg : Command("egg") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        channel.sendMessage(":egg:").submit()
    }
}