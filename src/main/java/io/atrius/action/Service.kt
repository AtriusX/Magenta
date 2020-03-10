package io.atrius.action

import io.atrius.CommandType
import net.dv8tion.jda.api.hooks.ListenerAdapter

abstract class Service(
    override val command    : String,
    override val description: String      = "Default description for $command",
    override val type       : CommandType = CommandType.ANY,
    override val permission : String?     = null
) : BaseCommand, ListenerAdapter()