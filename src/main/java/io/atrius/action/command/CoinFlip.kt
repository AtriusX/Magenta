package io.atrius.action.command

import io.atrius.action.Command
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import kotlin.random.Random

object CoinFlip : Command("flip") {

    private const val SUN = ":sun_with_face:"
    private const val MOON = ":new_moon_with_face:"

    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        channel.sendMessage(if (Random.nextBoolean()) "$SUN Heads!" else "$MOON Tails!").submit()
    }
}