package io.atrius.action

import io.atrius.manager.Bot

interface BotAction {
    val self
        get() = Bot.api.selfUser
}