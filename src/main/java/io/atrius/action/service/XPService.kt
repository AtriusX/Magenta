package io.atrius.action.service

import io.atrius.action.Service
import net.dv8tion.jda.api.entities.GuildChannel
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object XPService : Service("xp") {
    private val xp = hashMapOf<Long, Long>()

    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        val guild = (channel as GuildChannel).guild
        // Find the target or throw an error
        val target = if (args.isNotEmpty())
            guild.members.firstOrNull {
                it.nickname?.contains(args[0], true) ?: false || it.user.name.contains(args[0], true)
            }
        else guild.getMember(user)
        if (target == null) {
            channel.sendMessage("Couldn't find that user!").submit()
        } else {
            val amount = xp[target.idLong] ?: 0
            channel.sendMessage("${target.effectiveName} has $amount experience!").submit()
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) = event.author.run {
        // Update the experience amount
        xp[idLong] = (xp[idLong] ?: 0) + (5..50).random().toLong()
    }
}