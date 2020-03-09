package io.atrius.action.command

import io.atrius.action.Command
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import java.time.Duration
import java.time.LocalTime

object Uptime : Command("uptime") {
    private val init = LocalTime.now()

    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        val difference = Duration.between(init, LocalTime.now())
        channel.sendMessage("Bot has been online for ${difference.toHours()} hours and ${difference.toMinutes() % 60} minutes.").submit()
    }
}