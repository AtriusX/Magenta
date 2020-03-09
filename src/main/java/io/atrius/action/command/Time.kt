package io.atrius.action.command

import io.atrius.action.Command
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import java.time.LocalTime

object Time : Command("time") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        val time = LocalTime.now()
        channel.sendMessage(
                "The current time is ${time.hour % 12}:${time.minute} ${if (time.hour > 12) "P" else "A"}M"
        ).submit()
    }
}