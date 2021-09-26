package com.justin.materialchipsetwidget

import android.content.Context
import android.util.DisplayMetrics

object Utils {
    fun convertDpToPx(context: Context, dp: Int): Float {
        return dp * context.resources.displayMetrics.density
    }

    fun convertPixelsToDp(context: Context, px: Float): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}