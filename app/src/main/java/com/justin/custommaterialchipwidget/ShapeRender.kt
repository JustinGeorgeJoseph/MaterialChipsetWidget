package com.justin.custommaterialchipwidget

import android.graphics.drawable.Drawable
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class ShapeRender(val shape: Shape) {

    fun getDrawable(): Drawable {

        val shapeAppearanceModel = when (shape) {
            is CircleShape -> {
                createShapeAppearanceModel((shape.length / 2).toFloat())
            }
            is RectangleShape -> {
                createShapeAppearanceModel(shape.radius)
            }
            else -> {
                createShapeAppearanceModel(0f)
            }
        }
        return createMaterialShape(shapeAppearanceModel, shape)
    }

    private fun createShapeAppearanceModel(radius: Float) = ShapeAppearanceModel.Builder()
        .setAllCornerSizes(radius)
        .build()

    private fun createMaterialShape(shapeAppearanceModel: ShapeAppearanceModel, shape: Shape) =
        MaterialShapeDrawable(shapeAppearanceModel).apply {
            shape.backGroundTintColor?.let { setTint(it) }
            strokeWidth = shape.strokeWidth
            shape.strokeColor.also { strokeColor = it }
        }

}