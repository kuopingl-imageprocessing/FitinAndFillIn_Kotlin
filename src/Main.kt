//import ImageOperators.BilinearInterpolationOperator
//import ImageOperators.ImageOperator
//import java.io.File
//import javax.imageio.ImageIO
//
//fun main(args: Array<String>) {
//    val bilinearOperator = BilinearInterpolationOperator()
//    val lenna = File("Lenna100.jpg")  // from the Percentage difference between images task
//
//    val image = ImageIO.read(lenna)
//    val imageOperator = ImageOperator(image, bilinearOperator)
//    val image2 = imageOperator.scaleToFit(1.6f, 1.6f)
//    val lenna2 = File("Lenna100_larger.jpg")
//    ImageIO.write(image2, "jpg", lenna2)
//
//
//
//    val image3 = imageOperator.scaleToFit(0.3f, 0.6f)
//    val lenna3 = File("Lenna100_smaller.jpg")
//    ImageIO.write(image3, "jpg", lenna3)
//
//    val scaleToFit3_to_4 = imageOperator.scaleToFit(3f, 4f)
//    val lenna_scaleToFit3_to_4 = File("Lenna100_scaled_3to4.jpg")
//    ImageIO.write(scaleToFit3_to_4, "jpg", lenna_scaleToFit3_to_4)
//
//    val scaleToFit9_to_3 = imageOperator.scaleToFit(9f, 3f)
//    val lenna_scaleToFit9_to_3 = File("Lenna100_scaled_9to3.jpg")
//    ImageIO.write(scaleToFit9_to_3, "jpg", lenna_scaleToFit9_to_3)
//
//    val dragon = File("dragon100.jpg")
//    val dragonImg = ImageIO.read(dragon)
//    val dragonOperator = ImageOperator(dragonImg, bilinearOperator)
//    val dragon_3_to_10 = dragonOperator.scaleToFit(3f, 10f)
//    val dragon_scaleToFit_3_to_10 = File("Dragon_scaled_3to10.jpg")
//    ImageIO.write(dragon_3_to_10, "jpg", dragon_scaleToFit_3_to_10)
//
//    val dragon_scaleToFill_3_to_10 = dragonOperator.scaleToFill(3f, 10f)
//    val dragon_scaleToFill_3_to_10File = File("Dragon_scaledToFill_3to10.jpg")
//    ImageIO.write(dragon_scaleToFill_3_to_10, "jpg", dragon_scaleToFill_3_to_10File)
//
//    val dragon_scaleToFill_10_to_3 = dragonOperator.scaleToFill(10f, 3f)
//    val dragon_scaleToFill_10_to_3File = File("Dragon_scaledToFill_10_to_3.jpg")
//    ImageIO.write(dragon_scaleToFill_10_to_3, "jpg", dragon_scaleToFill_10_to_3File)
//}