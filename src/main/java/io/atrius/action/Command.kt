package io.atrius.action

import io.atrius.CommandType
import io.atrius.CommandType.ANY
import io.atrius.manager.Bot
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

abstract class Command(
        private val command    : String,
        private val description: String      = "Default description for $command",
        private val type       : CommandType = ANY,
        private val permission : String?     = null
) : BotAction {
    protected val self = Bot.api.selfUser

    abstract fun execute(args: Array<String>, channel: MessageChannel, user: User)

    operator fun component1() = command

    operator fun component2() = description

    operator fun component3() = type

    operator fun component4() = permission
}