# Fill in and Fit in Implementation with Bilinear Interpolation 

## How to use this ?
For demo, simply run FillinFitin.jar.

You can select scaling of both horizontal and vertical portions, 
then you can generate both Fill in and Fit in images 
simultaneously at the destination of your choice. 

<img src=https://github.com/kuopingl-imageprocessing/FitinAndFillIn_Kotlin/blob/master/demo.png/>

The source code is written in Kotlin, but the UI is written in Java.

## Implementation
In order to use this Image Operator, simply create a class that
implemented ImageOperation interface. 
(Inside you can find BilinearInterpolationOperator.kt, a class that had
 implemented ImageOperation and BilinearOperation.)

```kotlin
interface ImageOperations {
    fun scaleToFit(bufferedImage: BufferedImage, scaleX: Float, scaleY: Float): BufferedImage
    fun scaleToFill(bufferedImage: BufferedImage, scaleX: Float, scaleY: Float): BufferedImage
}
```

Then, create an ImageOperator that takes in classes that also
applied ImageOperations.

in Java
```java
File imageFile = File("Lenna100.jpg");
BufferedImage bufferedImage = ImageIO.read(imageFile);
BilinearInterpolationOperator biOp = new BilinearInterpolationOperator();
ImageOperator<BilinearInterpolationOperator> op = new ImageOperator<>(bufferedImage, biOp);
```
in Kotlin

```kotlin
val bilinearOperator = BilinearInterpolationOperator()
val lenna = File("Lenna100.jpg")
val image = ImageIO.read(lenna)
val imageOperator = ImageOperator(image, bilinearOperator)
```

Finally, just call scaleToFit or scaleToFill with the scaling portion you desired.

in Java
```java
BufferedImage filled = op.scaleToFill(1.6f, 1.6f);
BufferedImage fit = op.scaleToFit(1.6f, 1.6f);
```

in Kotlin
```kotlin
val filled = imageOperator.scaleToFill(1.6f, 1.6f)
val fit = imageOperator.scaleToFit(1.6f, 1.6f)
```

## Tests
You can find Tests for Bilinear Interpolation inside
BilinearInterpolationOperatorTest.kt.

Feel free to try it out.
