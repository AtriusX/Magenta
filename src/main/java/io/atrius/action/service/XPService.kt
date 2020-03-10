package io.atrius.action.service

import io.atrius.action.Service
import net.dv8tion.jda.api.entities.GuildChannel
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object XPService : Service("xp") {
    private val xp = hashMapOf<Long, Long>()

    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        // Make sure an argument is specified
        if (args.isEmpty())
            return
        // Find the target or throw an error
        val target  = (channel as GuildChannel).guild.getMembersByNickname(args[0], true).firstOrNull() ?: run {
            channel.sendMessage("Couldn't find that user!").submit()
            return
        }
        // Get the user's amount
        val amount = xp[target.idLong] ?: 0
        channel.sendMessage("${target.nickname} has $amount experience!").submit()
    }

    override fun onMessageReceived(event: MessageReceivedEvent) = event.author.run {
        // Update the experience amount
        xp[idLong] = (xp[idLong] ?: 0) + (5..50).random().toLong()
    }
}