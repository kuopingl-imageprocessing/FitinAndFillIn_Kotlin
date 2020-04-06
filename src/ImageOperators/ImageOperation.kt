package ImageOperators

import java.awt.Color
import java.awt.image.BufferedImage

interface ImageOperations {
    /**
     * @param bufferedImage: Image source
     * @param targetWidth: Width of the final image
     * @param targetHeight: Height of the final image
     * @return BufferedImage
     * */
    fun scaleToFit(bufferedImage: BufferedImage, scaleX: Float, scaleY: Float): BufferedImage
    /**
     * @param bufferedImage: Image source
     * @param scaleX: Scaling of X-axis
     * @param scaleY: Scaling of Y-axis
     * @return BufferedImage
     * */
    fun scaleToFill(bufferedImage: BufferedImage, scaleX: Float, scaleY: Float): BufferedImage
}