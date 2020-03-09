package io.atrius

import dungeonkit.Dungeon
import dungeonkit.DungeonKit
import dungeonkit.DungeonKit.random
import dungeonkit.data.Grid
import dungeonkit.data.by
import dungeonkit.data.steps.Denoise
import dungeonkit.data.steps.Eval
import dungeonkit.data.steps.MindlessWanderer
import dungeonkit.data.steps.Trim
import dungeonkit.data.tiles.Tile
import dungeonkit.data.tiles.Tiles
import dungeonkit.data.tiles.binding.CharTileMap
import dungeonkit.renderer.Renderer
import io.atrius.manager.Bot
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Icon
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import java.io.IOException
import java.net.URL
import java.time.Duration
import java.time.LocalTime
import kotlin.random.Random

fun main(args: Array<String>) = Bot.start(args) {
    Bot.register(
            Ping, Status, CoinFlip,
            Dungen, Time, Egg, PFP,
            Uptime
    )
}

object Ping : Command("ping") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {

        if (!user.isBot) channel.sendMessage(
                EmbedBuilder().appendDescription("Pong!").build()
        ).submit(true)
    }
}

object Status : Command("status") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        self.jda.presence.setPresence(
                if (args.isNotEmpty()) Activity.playing(args.joinToString(" ")) else null, false
        )
    }
}

object CoinFlip : Command("flip") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        channel.sendMessage(if (Random.nextBoolean()) ":sun_with_face: Heads!" else ":new_moon_with_face: Tails!").submit(true)

    }
}

object Dungen : Command("dungen") {

    private val dungeon: Dungeon<CharTileMap>
        get() = DungeonKit
            .create(dimension = 90 by 22, tileMap = CharTileMap(), seed = random.nextInt())
            .steps(MindlessWanderer(20, 450, newTileBias = 0.95, maxRetries = 10),
                    Trim, Denoise, Eval { _, _, tile -> if (tile == Tiles.FLOOR &&
                    random.nextInt(100) > 85) Tiles.EXIT else null })

    override fun execute(args: Array<String>, channel: MessageChannel, user: User) =
        dungeon.render(TextChannelRenderer(channel))
}

object Time : Command("time") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        val time = LocalTime.now()
        channel.sendMessage(
                "The current time is ${time.hour % 12}:${time.minute} ${if (time.hour > 12) "P" else "A"}M"
        ).submit(true)
    }
}

object Egg : Command("egg") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        channel.sendMessage(":egg:").submit(true)
    }
}

object PFP : Command("pfp") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        if (args.isEmpty())
            return
        try {
            self.manager.setAvatar(args[0].icon).submit(true)
            channel.sendMessage("Avatar updated!").submit(true)
        } catch (e: IOException) {
            channel.sendMessage("Couldn't set profile picture!").submit(true)
        }
    }
}

val String.icon: Icon
    get() = Icon.from(URL(this).openConnection().apply {
        setRequestProperty("User-Agent", "Mozilla/5.0") }.getInputStream())

object Uptime : Command("uptime") {
    private val init = LocalTime.now()

    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        val difference = Duration.between(init, LocalTime.now())
        channel.sendMessage("Bot has been online for ${difference.toHours()} hours and ${difference.toMinutes() % 60} minutes.").submit()
    }
}



data class TextChannelRenderer(
     val channel: MessageChannel
) : Renderer<CharTileMap> {

    override fun render(map: Grid<Tile>, tileMap: CharTileMap) {
        // Enforce the discord character limit
        val type = if (Random.nextBoolean()) "cs" else "fix"
        if (map.area.area > 1990)
            return
        var lines = ""
        val (w, h) = map.area
        val chars = Array(h) { Array(w) { tileMap.default.data } }
        map.forEach { (pos, tile) ->
            chars[pos.y][pos.x] = tileMap[tile].data
        }

        chars.forEach { c ->
            lines += "${c.map { "$it" }.reduce { a, b -> a + b }}\n"
        }
        channel.sendMessage("Here's your dungeon!\n```$type\n$lines\n```").submit(true)
    }
}