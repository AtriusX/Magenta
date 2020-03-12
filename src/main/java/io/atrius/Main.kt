package io.atrius

import io.atrius.action.command.*
import io.atrius.action.service.XPService
import io.atrius.manager.Bot

fun main(args: Array<String>) = Bot.start(args) {
    Bot.register(
            Ping, Status, CoinFlip,
            Dungen, Time, Egg, PFP,
            Uptime, XPService, DoItToEm,
            Google, RandomImage
    )

//    Events.register(XPService())
}