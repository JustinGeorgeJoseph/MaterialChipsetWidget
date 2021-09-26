package com.justin.materialchipsetwidget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat


class MaterialChipSetsContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : HorizontalScrollView(context, attrs) {

    var listener : MaterialChipsetWidgetListener? = null

    var dataSet: List<List<String>>? = null
        set(value) {
            field = value
            val chipSetList = arrayListOf<ChipSetData>()
            field?.let {
                for (i in it.indices)
                    chipSetList.add(ChipSetData(i, it[i]))
            }
            chipSetDataList = chipSetList
        }

    private var chipSetDataList: List<ChipSetData>? = null
        set(value) {
            field = value
            refreshWidgetViews(field)
        }

    private val chipSetWidgetList = arrayListOf<MaterialChipSetWidget>()
    private var closeButton: AppCompatImageView? = null

    var chipSetSolidColorUnselected: Int =
        ContextCompat.getColor(context, android.R.color.transparent)
    var chipSetSolidColorSelected: Int =
        ContextCompat.getColor(context, android.R.color.transparent)
    var chipSetStrokeWidth: Float = resources.getDimension(R.dimen.chipsetDefaultStrokeWidth)
    var chipSetStrokeColorUnselected: ColorStateList =
        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_gray))
    var chipSetStrokeColorSelected: ColorStateList =
        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_gray))

    var chipTextColorUnselected: Int = ContextCompat.getColor(context, R.color.light_gray)
    var chipTextColorSelected: Int = ContextCompat.getColor(context, R.color.light_gray)
    var chipTextDividerColorUnselected = ContextCompat.getColor(context, R.color.light_gray)

    @DrawableRes
    var closeButtonIcon: Int = R.drawable.ic_baseline_close_24

    private var selectedBackGroundDrawable: Drawable? = null
    private var unSelectedBackGroundDrawable: Drawable? = null


    private fun refreshWidgetViews(dataSetList: List<ChipSetData>?) {
        dataSetList?.let {
            removeAllViewsInLayout()
            addView(getContainerLayouts(it))
        }
    }


    private fun getContainerLayouts(dataSet: List<ChipSetData>): View {
        val containerLayout = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            this.orientation = LinearLayout.HORIZONTAL

            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.CENTER_VERTICAL
            layoutParams = params
        }

        containerLayout.addView(getCloseButton())

        chipSetWidgetList.addAll(getMaterialChipSetWidgets(context, dataSet))
        chipSetWidgetList.forEach { chipSetWidget ->
            containerLayout.addView(chipSetWidget)
        }

        return containerLayout
    }

    private fun getMaterialChipSetWidgets(
        context: Context,
        dataSet: List<ChipSetData>,
    ): List<MaterialChipSetWidget> {
        val widgetList = arrayListOf<MaterialChipSetWidget>()
        for (index in dataSet.indices) {
            val materialChipSetWidget = MaterialChipSetWidget(context).apply {

                 textColorUnselected  = chipTextColorUnselected
                 textColorSelected  = chipTextColorSelected
                 dividerColorUnselected = chipTextDividerColorUnselected

                background = getBackGroundDrawable(dataSet[index].isSelected)
                val padding = resources.getDimension(R.dimen.chipsetPadding).toInt()
                setPadding(padding, padding, padding, padding)
                val params = LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
                when(index) {
                    0 -> {
                        val leftMargin = (resources.getDimension(R.dimen.chipsetLeftMargin).toInt() *2)
                        val rightMargin = resources.getDimension(R.dimen.chipsetRightMargin).toInt()
                        params.setMargins(leftMargin, top, rightMargin, bottom)
                    }
                    dataSet.size -> {
                        val leftMargin = resources.getDimension(R.dimen.chipsetLeftMargin).toInt()
                        val rightMargin = (resources.getDimension(R.dimen.chipsetRightMargin).toInt() *2)
                        params.setMargins(leftMargin, top, rightMargin, bottom)
                    }
                    else -> {
                        val leftMargin = resources.getDimension(R.dimen.chipsetLeftMargin).toInt()
                        val rightMargin = resources.getDimension(R.dimen.chipsetRightMargin).toInt()
                        params.setMargins(leftMargin, top, rightMargin, bottom)
                    }
                }
                layoutParams = params
                this.chipSetWidgetListener = object : ChipSetWidgetListener {
                    override fun onChipClicked(position: Int, title: String?, isSelected: Boolean) {
                        updateChipsetWidgetStatus(position, isSelected)
                        sendChipStatusReturn(title,isSelected)
                    }
                }
                this.chipDataSet = dataSet[index]
            }

            widgetList.add(materialChipSetWidget)
        }
        return widgetList
    }

    private fun sendChipStatusReturn(title : String?, selected: Boolean) = listener?.onChipSelectionChanged(title,selected)

    private fun updateChipsetWidgetStatus(position: Int, selected: Boolean) {
        chipSetWidgetList[position].background = getBackGroundDrawable(selected)
        chipSetDataList?.get(position)?.isSelected = selected
        updateCloseButtonVisibility()
    }


    private fun updateCloseButtonVisibility() {

        chipSetDataList?.let {
            for (chipSetData in it) {
                if (chipSetData.isSelected) {
                    closeButton?.visibility = View.VISIBLE
                    return
                }
            }
        }
        closeButton?.visibility = View.GONE
    }

    private fun getCloseButton(): View? {
        closeButton = AppCompatImageView(context).apply {
            setImageResource(closeButtonIcon)
            val padding = resources.getDimension(R.dimen.chipsetCloseButtonPadding).toInt()
            setPadding(padding, padding, padding, padding)
            val length = resources.getDimension(R.dimen.chipsetCloseButtonLength).toInt()
            val params = LinearLayout.LayoutParams(length, length)
            params.gravity = Gravity.CENTER_VERTICAL
            imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context,android.R.color.white))
            layoutParams = params
            background = getSelectedShapeBackground(chipSetSolidColorSelected,
                chipSetStrokeColorSelected,
                chipSetStrokeWidth)
            visibility = View.GONE
            setOnClickListener {
                resetChipsetWidgetStatus()
            }
        }
        return closeButton
    }

    private fun resetChipsetWidgetStatus() {
        listener?.onChipsetsResetClicked()
        chipSetDataList?.apply {
            for (index in this.indices) {
                updateChipsetWidgetStatus(index,false)
            }
        }
        chipSetWidgetList.forEach {
            it.refreshData()
        }
        closeButton?.visibility = View.GONE
    }


    private fun getBackGroundDrawable(selected: Boolean): Drawable? {
        return if (selected) {
            if (selectedBackGroundDrawable == null)
                selectedBackGroundDrawable = getSelectedShapeBackground(chipSetSolidColorSelected,
                    chipSetStrokeColorSelected,
                    chipSetStrokeWidth)
            selectedBackGroundDrawable
        } else {
            if (unSelectedBackGroundDrawable == null)
                unSelectedBackGroundDrawable = getSelectedShapeBackground(
                    chipSetSolidColorUnselected,
                    chipSetStrokeColorUnselected,
                    chipSetStrokeWidth)
            unSelectedBackGroundDrawable
        }
    }

    private fun getSelectedShapeBackground(
        solidColor: Int, strokeColor: ColorStateList,
        strokeWidth: Float,
    ): Drawable {
        val rectangleShape = RectangleShape(radius = resources.getDimension(R.dimen.radius)).apply {
            this.backGroundTintColor = solidColor
            this.strokeWidth = strokeWidth
            this.strokeColor = strokeColor
        }
        return ShapeRender(rectangleShape).getDrawable()
    }

}