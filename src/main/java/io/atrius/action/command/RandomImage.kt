package io.atrius.action.command

import io.atrius.action.Command
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import kotlin.random.Random

object RandomImage : Command("randomimage") {
    override fun execute(args: Array<String>, channel: MessageChannel, user: User) {
        val width  = 500
        val height = 500
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

        val step = 50
        for (y in 0 until width step step) {
            for (x in 0 until height step step) {
                img.square(x, y, step, (0xFF shl 24) or Random.nextInt(0xFFFFFF))
            }
        }
        channel.sendFile(img.toByteArray(), "img.png").submit()
    }


}

fun BufferedImage.square(x: Int, y: Int, size: Int, color: Int) {
    for (dx in x until x + size) for (dy in y until y + size) setRGB(dx, dy, color)
}

fun BufferedImage.toByteArray(): ByteArray = ByteArrayOutputStream()
        .also { ImageIO.write(this, "png", it) }.toByteArray()