package io.atrius

import io.atrius.CommandType.*
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

@Suppress("MemberVisibilityCanBePrivate")
abstract class Command(
        internal val command    : String,
        internal val description: String      = "Default description for $command",
        internal val type       : CommandType = ANY,
        internal val permission : String?     = null
) {

    abstract fun execute(args: Array<String>, channel: MessageChannel, user: User)

    operator fun component1() = command

    operator fun component2() = description

    operator fun component3() = type

    operator fun component4() = permission
}

val MessageChannel.context: CommandType
    get() = if (type.isGuild) GUILD else PRIVATE