import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val image = ImageIO.read(File("-specify the path to the photo-")) // Укажите путь к изображению

    val width = image.width
    val height = image.height

    val asciiWidth = 100// Ширина ASCII-арт в символах
    val asciiHeight = (height.toDouble() * asciiWidth.toDouble() / width.toDouble() * 0.6).toInt() // Соотношение сторон изображения

    val resizedImage = resizeImage(image, asciiWidth, asciiHeight)
    val grayscaleImage = convertToGrayscale(resizedImage)
    val asciiArt = convertToAscii(grayscaleImage)

    println(asciiArt)
}

fun resizeImage(image: BufferedImage, width: Int, height: Int): BufferedImage {
    val resizedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics = resizedImage.createGraphics()
    graphics.drawImage(image, 0, 0, width, height, null)
    graphics.dispose()
    return resizedImage
}

fun convertToGrayscale(image: BufferedImage): BufferedImage {
    val grayscaleImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_BYTE_GRAY)
    val graphics = grayscaleImage.createGraphics()
    graphics.drawImage(image, 0, 0, null)
    graphics.dispose()
    return grayscaleImage
}

fun convertToAscii(image: BufferedImage): String {
    val asciiChars = "@%#*+=-:. "
    val sb = StringBuilder()

    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val color = Color(image.getRGB(x, y))
            val luminance = (color.red + color.green + color.blue) / 3

            val index = (luminance.toFloat() / 255.toFloat() * (asciiChars.length - 1)).toInt()
            sb.append(asciiChars[index])
        }
        sb.append("\n")
    }

    return sb.toString()
}