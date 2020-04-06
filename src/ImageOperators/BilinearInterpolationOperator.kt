package ImageOperators

import java.awt.Color
import java.awt.image.BufferedImage

class BilinearInterpolationOperator : ImageOperations, BilinearOperation {

    /**
     * This function is used to Scale the image and apply Fit in.
     *
     * @param bufferedImage : Buffered Image derived from file
     * @param scaleX : Scale on the X-axis
     * @param scaleY : Scale on the Y-axis
     * @return Buffered Image
     * */
    override fun scaleToFit(bufferedImage: BufferedImage, scaleX: Float, scaleY: Float): BufferedImage {
        val width = bufferedImage.width
        val height = bufferedImage.height

        val newWidth = (width * scaleX).toInt()
        val newHeight = (height * scaleY).toInt()

        if (scaleX <= 0f || scaleY <= 0f || (newWidth == width && newHeight == height))
            return bufferedImage

        val newImage = BufferedImage(newWidth, newHeight, bufferedImage.type)

        val ratio = width.toFloat() / height.toFloat()
        // with width dominates
        var targetImageHeight = newHeight
        var targetImageWidth = (targetImageHeight * ratio).toInt()

        var startingPoint = (newWidth - targetImageWidth) / 2
        var endingPoint = (newWidth + targetImageWidth) / 2

        if (newWidth.toFloat() / newHeight.toFloat() < 1.0f) {
            // Height dominates
            targetImageWidth = newWidth
            targetImageHeight = (targetImageWidth / ratio).toInt()
            startingPoint = (newHeight - targetImageHeight) / 2
            endingPoint = (newHeight + targetImageHeight) / 2
        }

        for (x in 0 until newWidth) {
            for (y in 0 until newHeight) {
                var rgb = Color(255, 255, 255).rgb

                if (targetImageWidth == newWidth) {
                    // checkout if the height is where to put the image
                    if (y in startingPoint until endingPoint) {
                        val (px, py) =
                            findPxyCoordinate(x, y - startingPoint,
                                Pair(targetImageWidth, targetImageHeight),
                                Pair(width, height))
                        rgb = analyzeRGB(bufferedImage, px, py)
                    }
                } else {
                    if (x in startingPoint until endingPoint) {

                        val (px, py) =
                            findPxyCoordinate(x - startingPoint, y,
                                Pair(targetImageWidth, targetImageHeight),
                                Pair(width, height))
                        rgb = analyzeRGB(bufferedImage, px, py)
                    }
                }
                newImage.setRGB(x, y, rgb)
            }
        }
        return newImage
    }

    /**
     * This function is used to Scale the image and apply Fill in.
     *
     * @param bufferedImage : Buffered Image derived from file
     * @param scaleX : Scale on the X-axis
     * @param scaleY : Scale on the Y-axis
     * @return Buffered Image
     * */
    override fun scaleToFill(bufferedImage: BufferedImage, scaleX: Float, scaleY: Float): BufferedImage {
        val width = bufferedImage.width
        val height = bufferedImage.height

        val newWidth = (width * scaleX).toInt()
        val newHeight = (height * scaleY).toInt()

        if (scaleX <= 0f || scaleY <= 0f || (newWidth == width && newHeight == height))
            return bufferedImage

        val newImage = BufferedImage(newWidth, newHeight, bufferedImage.type)

        val ratio = width.toFloat() / height.toFloat()
        // with width dominates
        var targetImageWidth = newWidth
        var targetImageHeight = (targetImageWidth / ratio).toInt()
        var startingPoint = (targetImageHeight - newHeight) / 2

        if (newWidth.toFloat() / newHeight.toFloat() < 1.0f) {
            // Height dominates
            targetImageHeight = newHeight
            targetImageWidth = (targetImageHeight * ratio).toInt()
            startingPoint = (targetImageWidth - newWidth) / 2
        }

        for (x in 0 until newWidth) {
            for (y in 0 until newHeight) {
                val rgb =
                    if (targetImageWidth == newWidth) {
                    val (px, py) =
                        findPxyCoordinate(x, y + startingPoint,
                            Pair(targetImageWidth, targetImageHeight),
                            Pair(width, height))
                    analyzeRGB(bufferedImage, px, py)
                    } else {
                        val (px, py) =
                            findPxyCoordinate(x + startingPoint, y,
                                Pair(targetImageWidth, targetImageHeight),
                                Pair(width, height))
                        analyzeRGB(bufferedImage, px, py)
                    }

                newImage.setRGB(x, y, rgb)
            }
        }
        return newImage
    }

    /**
     * This function is to find the proper RGB: Int value at the Final Image from the Source Image
     *
     * @param bufferedImage: Buffered Image derived from File
     * @param px : X-coordinate of the point of interest
     * @param py : Y-coordinate of the point of interest
     * @param initRGB : initial value of the RGB
     * @return Int : final RGB after finding the proper X,Y coordinate of the Source Image
     */
    private fun analyzeRGB(bufferedImage: BufferedImage,
                           px: Float, py: Float, initRGB: Int = 0): Int {
        var newRGB = initRGB
        val (x1, y1) = findReferenceXY(Pair(px, py), Pair(bufferedImage.width, bufferedImage.height))

        val rgb00 = bufferedImage.getRGB(x1 , y1)
        val rgb10 = bufferedImage.getRGB(x1 + 1, y1)
        val rgb01 = bufferedImage.getRGB(x1, y1 + 1)
        val rgb11 = bufferedImage.getRGB(x1 + 1, y1 + 1)
        for (i in 0..2) {
            val q00 = rgb00[i].toFloat()
            val q10 = rgb10[i].toFloat()
            val q01 = rgb01[i].toFloat()
            val q11 = rgb11[i].toFloat()
            val ble = bilinearInterpolation(q00, q10, q01, q11, px - x1, py - y1).toInt() shl (8 * i)
            newRGB = newRGB or ble
        }
        return newRGB
    }
}