package io.atrius.manager

import io.atrius.action.BotAction
import io.atrius.action.Command
import io.atrius.action.Listener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder

private typealias Token = String

object Bot {

    lateinit var api: JDA
        private set

    fun start(args: Array<String>, block: () -> Unit) = if (args.isEmpty())
        error("Argument mismatch: [Token]") else login(args[0]).also { block() }

    private fun login(token: Token) {
        JDABuilder(token).build().apply {
            addEventListener(Commands)
            api = this
        }
    }

    fun register(vararg actions: BotAction) {
        for (action in actions) when(action) {
            is Command -> Commands.register(action)
            is Listener -> Events.register(action)
        }
    }
}
