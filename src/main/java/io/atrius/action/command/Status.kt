package io.atrius.action.command

import io.atrius.action.Command
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

object Status : Command("status") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        self.jda.presence.setPresence(
                if (args.isNotEmpty()) Activity.playing(args.joinToString(" ")) else null, false
        )
    }
}