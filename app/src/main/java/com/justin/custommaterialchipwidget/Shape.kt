package com.justin.custommaterialchipwidget

import android.content.res.ColorStateList

open class Shape {
    var backGroundTintColor : Int? = null
    var strokeWidth : Float  = 0f
    var strokeColor : ColorStateList? = null
}
data class CircleShape(val length : Int = 0) : Shape()
data class RectangleShape(val width: Int = 0, val height : Int = 0, val radius : Float = 0f) : Shape()

