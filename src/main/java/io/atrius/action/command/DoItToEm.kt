package io.atrius.action.command

import io.atrius.action.Command
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

object DoItToEm : Command("doittoem") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        channel.sendMessage(
                "<:hadtodoittoem1:529761418866851851>\n<:hadtodoittoem2:529761420900827145>\n<:hadtodoittoem3:529761422322827274>"
        ).submit()
    }
}