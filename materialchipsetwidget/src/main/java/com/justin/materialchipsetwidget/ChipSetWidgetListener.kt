package com.justin.materialchipsetwidget

interface ChipSetWidgetListener {
    fun onChipClicked(position : Int,chipSelectionGroup:String, title : String?, isSelected :Boolean)
}
