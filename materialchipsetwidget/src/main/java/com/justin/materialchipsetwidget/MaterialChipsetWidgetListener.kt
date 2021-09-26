package com.justin.materialchipsetwidget

interface MaterialChipsetWidgetListener {
    fun onChipSelectionChanged(name : String?, checked : Boolean)
    fun onChipsetsResetClicked()
}