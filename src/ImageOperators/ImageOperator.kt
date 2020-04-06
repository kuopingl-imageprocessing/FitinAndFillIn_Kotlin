package ImageOperators

import java.awt.image.BufferedImage

/* gets the 'n'th byte of a 4-byte integer */
/**
 * This function is to extract 4 byte integer from any Int
 * @param n : Shift the bit by n * 8
 */
operator fun Int.get(n: Int) = (this shr (n * 8)) and 0xFF

/***
 * @constructor (private val bufferedImage: BufferedImage, private val operator: T)
 * @property bufferedImage : Buffered Image derived from File
 * @param operator : An object that implemented ImageOperations
 */
class ImageOperator<T: ImageOperations> (private val bufferedImage: BufferedImage,
                                                        private val operator: T) {
    /**
     * This function is to display image as Fit in
     *
     * @param scaleX: scale in the X-axis
     * @param scaleY: scale in the Y-axis
     * @return BufferedImage
     * */
    fun scaleToFit(scaleX: Float, scaleY: Float): BufferedImage {
        return operator.scaleToFit(bufferedImage, scaleX, scaleY)
    }

    /**
     * This function is to display image as Fit in
     *
     * @param scaleX: scale in the X-axis
     * @param scaleY: scale in the Y-axis
     * @return BufferedImage
     * */
    fun scaleToFill(scaleX: Float, scaleY: Float): BufferedImage {
        return operator.scaleToFill(bufferedImage, scaleX, scaleY)
    }
}


