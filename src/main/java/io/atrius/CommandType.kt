package io.atrius

import net.dv8tion.jda.api.entities.ChannelType

enum class CommandType {
    PRIVATE, GUILD, ANY;

    infix fun meets(type: ChannelType) = when(this) {
        PRIVATE -> !type.isGuild
        GUILD   -> type.isGuild
        else    -> true
    }
}
