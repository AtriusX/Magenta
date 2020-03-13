package io.atrius.action.command

import dungeonkit.Dungeon
import dungeonkit.DungeonKit
import dungeonkit.DungeonKit.random
import dungeonkit.data.Grid
import dungeonkit.data.by
import dungeonkit.data.steps.BinarySplit
import dungeonkit.data.steps.Denoise
import dungeonkit.data.steps.Eval
import dungeonkit.data.steps.Trim
import dungeonkit.data.tiles.Tile
import dungeonkit.data.tiles.Tiles
import dungeonkit.data.tiles.binding.SimpleCharTileMap
import dungeonkit.data.tiles.binding.SimpleColorTileMap
import dungeonkit.renderer.Renderer
import io.atrius.action.Command
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import java.awt.image.BufferedImage
import kotlin.random.Random

object Dungen : Command("dungen") {

    private val dungeon: Dungeon<SimpleColorTileMap>
        get() = DungeonKit
                .create(dimension = 50 by 30, tileMap = SimpleColorTileMap, seed = random.nextInt())
                .steps(/*MindlessWanderer(50, 650, newTileBias = 0.9, maxRetries = 10)*/BinarySplit(2),
                        Trim, Denoise, Eval { _, _, tile -> if (tile == Tiles.FLOOR &&
                        random.nextInt(100) > 85) Tiles.EXIT else null })

    override fun execute(args: Array<String>, channel: MessageChannel, user: User) =
            dungeon.render(ImageRenderer(channel))
}

data class TextChannelRenderer(
        val channel: MessageChannel
) : Renderer<SimpleCharTileMap> {

    override fun render(map: Grid<Tile>, tileMap: SimpleCharTileMap) {
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

data class ImageRenderer(
        val channel  : MessageChannel,
        val scale    : Int = 25
) : Renderer<SimpleColorTileMap> {

    override fun render(map: Grid<Tile>, tileMap: SimpleColorTileMap) {
        val (width, height) = map.area
        val img    = BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_RGB)
        for (y in 0 until height)
            for (x in 0 until width)
                img.square(x * scale, y * scale, scale, tileMap[map[x, y]].data)
        channel.sendFile(img.toByteArray(), "dungeon.png").submit()
    }
}