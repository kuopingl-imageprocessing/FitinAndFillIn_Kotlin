package ImageOperators

interface BilinearOperation {
    // https://en.wikipedia.org/wiki/Bilinear_interpolation
    // Setup the formula 1-D Linear Interpolation
    // interpolation result = f(x1,y1) + (f(x2,y1) - f(x1, y1)) * (x - x1) / (x2 - x1)
    // w is the weight

    /**
     * @see [https://en.wikipedia.org/wiki/Bilinear_interpolation]
     * This function is based on the 1D Linear Interpolation
     * interpolation result = f(x1,y1) + (f(x2,y1) - f(x1, y1)) * (x - x1) / (x2 - x1)
     *
     * @param f1 : f(x1,y1)
     * @param f2 : f(x2,y1)
     * @param w : this is the WEIGHT of (f2 - f1), which is (x - x1) / (x2 - x1)
     *
     * @return interpolation value : f(x1,y1) + (f(x2,y1) - f(x1, y1)) * (x - x1) / (x2 - x1)
     * */
    fun linearInterpolation(f1: Float, f2: Float, w: Float) = f1 + (f2 - f1) * w

    /***
     * @see [https://en.wikipedia.org/wiki/Bilinear_interpolation]
     * This function is based on the Bilinear Interpolation.
     * Basically, it is taking two 1D Linear Interpolation from X-axis
     * and applied another 1D Linear Interpolation on the Y-axis
     *
     * @param q00 : value from the bottom-left corner of the value of interest
     * @param q01 : value from the top-left corner of the value of interest
     * @param q10 : value from the bottom-right corner of the value of interest
     * @param q11 : value from the top-right corner of the value of interest
     * @param wx : value of the WEIGHT on X-axis, which is (x - x1) / (x2 - x1)
     * @param wy : value of the WEIGHT on Y-axis which is (y - y1) / (y2 - y1)
     *
     * @return Bilinear Interpolation values based on [q00, q01, q11,q10] and wx and wy.
     */
    fun bilinearInterpolation(q00: Float, q10: Float, q01: Float, q11: Float,
                              wx: Float, wy: Float)
            = linearInterpolation(linearInterpolation(q00, q10, wx), linearInterpolation(q01,q11, wx), wy)


    fun findPxyCoordinate(x0: Int, y0: Int,
                          targetWH: Pair<Int, Int>,
                          sourceWH: Pair<Int, Int>): Pair<Float, Float> {
        //TODO: Apply Align-Corner = false
        // -1 so we won't ImageOperators.get out of boundary
        return Pair(
            x0.toFloat()/(targetWH.first.toFloat() - 1) * (sourceWH.first.toFloat() - 1),
            y0.toFloat()/(targetWH.second.toFloat() - 1) * (sourceWH.second.toFloat() - 1)
        )
    }

    fun findReferenceXY(pXY: Pair<Float, Float>, sourceWH: Pair<Int, Int>): Pair<Int, Int> {
        var x1 = pXY.first.toInt()
        var y1 = pXY.second.toInt()

        if (x1 >= sourceWH.first- 1) {
            x1 = sourceWH.first - 2
        }

        if (y1 >= sourceWH.second - 1) {
            y1 = sourceWH.second - 2
        }
        return Pair(x1, y1)
    }
}