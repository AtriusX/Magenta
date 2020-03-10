package io.atrius.action

import io.atrius.CommandType

abstract class Command(
        override val command    : String,
        override val description: String      = "Default description for $command",
        override val type       : CommandType = CommandType.ANY,
        override val permission : String?     = null
) : BaseCommand