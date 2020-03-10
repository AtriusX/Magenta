package io.atrius.action

import io.atrius.CommandType
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User


interface BaseCommand : BotAction {

    val command: String

    val description: String
        get() = "Default description for $command"

    val type: CommandType
        get() = CommandType.ANY

    val permission: String?
        get() = null

    operator fun component1() = command

    operator fun component2() = description

    operator fun component3() = type

    operator fun component4() = permission

    fun execute(args: Array<String>, channel: MessageChannel, user: User)
}