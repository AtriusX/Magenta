package io.atrius

import net.dv8tion.jda.api.entities.Icon
import net.dv8tion.jda.api.entities.MessageChannel
import java.net.URL

val String.icon: Icon
    get() = Icon.from(URL(this).openConnection().apply {
        setRequestProperty("User-Agent", "Mozilla/5.0") }.getInputStream())

val MessageChannel.context: CommandType
    get() = if (type.isGuild) CommandType.GUILD else CommandType.PRIVATE