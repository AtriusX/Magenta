package io.atrius

import io.atrius.CommandType.PRIVATE
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import kotlin.math.min

@Suppress("unused")
object CommandManager : Iterable<Command>, ListenerAdapter() {

    private val commands = arrayListOf<Command>()

    val activeCommands: Int
        get() = commands.size

    fun register(vararg commands: Command) =
            this.commands.addAll(commands)

    fun unregister(vararg commands: Command) =
            this.commands.removeAll(commands)

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val content = event.message.contentRaw
        val args = content.split(" ").run {
            when {
                isEmpty() -> emptyList()
                else      -> subList(min(size, 1), size)
            }
        }
        // Loop over all commands
        for (c in this) {
            val (command, _, type) = c
            // Check to see if the commands match
            if (!content.startsWith("!$command", true)) {
                continue
            }
            // Skip over commands that don't fit the right channel type
            if (!event.channelType.isGuild && type == PRIVATE) {
                continue
            }
            // Execute the command
            c.execute(args.toTypedArray(), event.channel, event.author)
        }
    }

    override fun iterator(): Iterator<Command> = commands.iterator()
}

fun User.hasPermission(permission: String): Unit = TODO()