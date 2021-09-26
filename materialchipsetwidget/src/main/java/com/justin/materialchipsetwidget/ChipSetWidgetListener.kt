package com.justin.materialchipsetwidget

interface ChipSetWidgetListener {
    fun onChipClicked(position : Int, title : String?, isSelected :Boolean)
}