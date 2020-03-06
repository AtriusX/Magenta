package io.atrius

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import kotlin.math.min

object Bot {

    lateinit var api: JDA
        private set

    fun login(token: Token, settings: JDABuilder.() -> Unit = {}) {
        JDABuilder(token).apply(settings).build().apply {
            addEventListener(CommandManager)
            api = this
        }
    }
}

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
            // Skip if the commands or channel type don't match
            if (!content.startsWith("!$command", true) || !type.meets(event.channelType))
                continue
            // Execute the command
            c.execute(args.toTypedArray(), event.channel, event.author)
        }
    }

    override fun iterator(): Iterator<Command> = commands.iterator()
}

fun User.hasPermission(permission: String): Unit = TODO()