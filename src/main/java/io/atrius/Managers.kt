package io.atrius

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.logging.Logger
import kotlin.math.min

private typealias Token = String

object Bot {

    lateinit var api: JDA
        private set

    fun start(args: Array<String>, block: () -> Unit) = if (args.isEmpty())
        error("Argument mismatch: [Token]") else login(args[0]).also { block() }

    private fun login(token: Token) {
        JDABuilder(token).build().apply {
            addEventListener(CommandManager)
            api = this
        }
    }
}

object CommandManager : Iterable<Command>, ListenerAdapter() {

    private val logger   = Logger.getLogger("CM")
    private val commands = arrayListOf<Command>()

    val activeCommands: Int
        get() = commands.size

    fun register(vararg commands: Command) =
            this.commands.addAll(commands)

    fun unregister(vararg commands: Command) =
            this.commands.removeAll(commands)

    override fun onMessageReceived(event: MessageReceivedEvent) = event.run {
        // Skip any bot accounts
        if (author.isBot) return
        // Process message
        val content = message.contentRaw
        val args = content.split(" ").run {
            when {
                isEmpty() -> emptyList()
                else      -> subList(min(size, 1), size)
            }
        }
        // Loop over all commands
        for (c in commands) {
            val (command, _, type) = c
            // Skip if the commands or channel type don't match
            if (!content.startsWith("!$command", true) || !type.meets(channelType))
                continue
            // Execute the command
            c.execute(args.toTypedArray(), channel, author)
            logger.info("'$command' executed by ${author.name} in #${channel.name}")
            return
        }
    }

    override fun iterator(): Iterator<Command> = commands.iterator()
}

fun User.hasPermission(permission: String): Unit = TODO()