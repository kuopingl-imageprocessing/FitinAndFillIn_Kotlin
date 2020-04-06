package ImageOperators

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BilinearInterpolationOperatorTest: BilinearOperation {
    @Test fun testLinearInterpolation() {
        val f: (x: Float) -> Float = {
            it * 10
        }
        val x = 0.4f

        val x1 = 0f
        val f1 = f(x1)
        val x2 = 10f
        val f2 = f(x2)

        val w = (x - x1).toFloat() / (x2 - x1).toFloat()

        assertEquals(f(x), linearInterpolation(f1, f2, w), "testLinearInterpolation")
    }

    @Test fun testBilinearInterpolationScaleUp() {
        val originList = listOf<List<Float>>(listOf(1f, 2f), listOf(3f, 4f))
        val resultList = mutableListOf<MutableList<Float>>(
            mutableListOf(0f, 0f, 0f, 0f),
            mutableListOf(0f, 0f, 0f, 0f),
            mutableListOf(0f, 0f, 0f, 0f),
            mutableListOf(0f, 0f, 0f, 0f))
        // http://wykvictor.github.io/2018/10/01/Bilinear-Interpolation.html
        val expected = listOf<List<Float>>(
            listOf(1.0000f,  1.3333f,  1.6667f,  2.0000f),
            listOf(1.6667f,  2.0000f,  2.3333f,  2.6667f),
            listOf(2.3333f,  2.6667f,  3.0000f,  3.3333f),
            listOf(3.0000f,  3.3333f,  3.6667f,  4.0000f))

        for (y in resultList.indices) {
            for (x in resultList[y].indices) {
                val (px, py) =
                    findPxyCoordinate(x, y,
                    Pair(resultList[0].size, resultList.size),
                    Pair(originList[0].size, originList.size)
                    )

                val (x1, y1) = findReferenceXY(Pair(px, py), Pair(originList[0].size, originList.size))

                val q00 = originList[y1][x1]
                val q01 = originList[y1 + 1][x1]
                val q10 = originList[y1][x1 + 1]
                val q11 = originList[y1 + 1][x1 + 1]
                val result = bilinearInterpolation(
                    q00 = q00, q01 = q01,
                    q10 = q10, q11 = q11,
                    wx = px - x1, wy = py - y1)
                resultList[y][x] = result

                assertEquals(expected[y][x], result, 0.001f)
            }
        }
    }

    @Test fun testBilinearInterpolationScaleDown() {
        val originList = listOf<List<Float>>(
            listOf(1.0000f,  1.3333f,  1.6667f,  2.0000f),
            listOf(1.6667f,  2.0000f,  2.3333f,  2.6667f),
            listOf(2.3333f,  2.6667f,  3.0000f,  3.3333f),
            listOf(3.0000f,  3.3333f,  3.6667f,  4.0000f))
        val resultList = mutableListOf<MutableList<Float>>(
            mutableListOf(0f, 0f),
            mutableListOf(0f, 0f))
        val expected = listOf<List<Float>>(listOf(1f, 2f), listOf(3f, 4f))

        for (y in resultList.indices) {
            for (x in resultList[y].indices) {
                val (px, py) =
                    findPxyCoordinate(x, y,
                        Pair(resultList[0].size, resultList.size),
                        Pair(originList[0].size, originList.size)
                    )

                val (x1, y1) = findReferenceXY(Pair(px, py), Pair(originList[0].size, originList.size))

                val q00 = originList[y1][x1]
                val q01 = originList[y1 + 1][x1]
                val q10 = originList[y1][x1 + 1]
                val q11 = originList[y1 + 1][x1 + 1]
                val result = bilinearInterpolation(
                    q00 = q00, q01 = q01,
                    q10 = q10, q11 = q11,
                    wx = px - x1, wy = py - y1)
                resultList[y][x] = result
                assertEquals(expected[y][x], result, 0.001f)
            }
        }
    }
}