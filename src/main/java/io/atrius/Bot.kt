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
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import kotlin.random.Random

fun main(args: Array<String>) = Bot.start(args) {
    CommandManager.register(Ping, Status, CoinFlip, Dungen)
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
        Bot.api.presence.setPresence(
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

    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        dungeon.render(TextChannelRenderer(channel))
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