package io.atrius.manager

import io.atrius.action.BaseCommand
import io.atrius.action.BotAction
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.ListenerAdapter

private typealias Token = String

object Bot {

    lateinit var api: JDA
        private set

    fun start(args: Array<String>, block: () -> Unit) = if (args.isEmpty())
        error("Argument mismatch: [Token]") else login(args[0]).also { block() }

    private fun login(token: Token) {
        api = JDABuilder(token).build().apply {
            addEventListener(Commands, Events)
        }
    }

    fun register(vararg actions: BotAction) = actions.forEach {
        if (it is BaseCommand)
            Commands.register(it)
        if (it is ListenerAdapter)
            Events.register(it)
    }
}
