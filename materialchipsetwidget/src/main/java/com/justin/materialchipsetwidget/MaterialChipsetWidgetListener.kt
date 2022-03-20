package com.justin.materialchipsetwidget

interface MaterialChipsetWidgetListener {

    fun onChipSelectionChanged(name: String?, chipSelectionGroup: String, checked: Boolean)

    fun onChipsetsResetClicked()

}
