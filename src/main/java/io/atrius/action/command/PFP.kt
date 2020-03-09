package io.atrius.action.command

import io.atrius.action.Command
import io.atrius.icon
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import java.io.IOException

object PFP : Command("pfp") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        if (args.isEmpty())
            return
        try {
            self.manager.setAvatar(args[0].icon).submit()
            channel.sendMessage("Avatar updated!").submit()
        } catch (e: IOException) {
            channel.sendMessage("Couldn't set profile picture!").submit()
        }
    }
}