package com.justin.custommaterialchipwidget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat


data class ChipSetData(val position : Int, val chipList : List<String>, var isSelected : Boolean = false,  var selectedPosition :Int =-1)

class MaterialChipSetsContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : HorizontalScrollView(context, attrs) {

    var dataSet: List<List<String>>? = null
        set(value) {
            field = value
            val chipSetList = arrayListOf<ChipSetData>()
            field?.let {
                for (i in it.indices)
                    chipSetList.add(ChipSetData(i,it[i]))
            }
            chipSetDataList = chipSetList
        }

    private var chipSetDataList : List<ChipSetData>? =null
    set(value) {
        field = value
        refreshWidgetViews(field)
    }

    private val chipSetWidgetList = arrayListOf<MaterialChipSetWidget>()
    private var closeButton :AppCompatImageView? = null

/*    private fun refreshWidgetView(dataSetList: List<List<String>>?) {
        dataSetList?.let {
            removeAllViewsInLayout()
            addView(getContainerLayout(it))
        }
    }*/

    private fun refreshWidgetViews(dataSetList: List<ChipSetData>?) {
        dataSetList?.let {
            removeAllViewsInLayout()
            addView(getContainerLayouts(it))
        }
    }



  /*  private fun getContainerLayout(dataSet: List<List<String>>): View {
        val containerLayout = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            this.orientation = LinearLayout.HORIZONTAL

            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.CENTER_VERTICAL
            layoutParams = params
        }

        dataSet.forEach {
            val materialChipSetWidget = MaterialChipSetWidget(context).apply {
                this.chipSetWidgetListener = object : ChipSetWidgetListener {
                    override fun onChipClicked(position: Int, title: String?, isSelected: Boolean) {
                    }
                }
                this.dataSet = it
                background = getShapeBackground(context)
                setPadding(5, 5, 5, 5)
                val params = LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
                //params.setMargins(20, top, right, bottom)
                layoutParams = params

            }

            containerLayout.addView(materialChipSetWidget)
        }

        return containerLayout
    }*/

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

        dataSet.forEach {
            val materialChipSetWidget = MaterialChipSetWidget(context).apply {
                this.chipDataSet = it
                background = getShapeBackground(context)
                //TODO remove this hardcoded code
                setPadding(5,5,5,5)
                val params = LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
                //TODO remove this hardcoded code
                //params.setMargins(20, top, right, bottom)
                layoutParams = params
                this.chipSetWidgetListener = object : ChipSetWidgetListener {
                    override fun onChipClicked(position: Int, title: String?, isSelected: Boolean) {
                        updateChipsetWidgetStatus(position,title,isSelected)
                    }
                }
            }

            chipSetWidgetList.add(materialChipSetWidget)

            containerLayout.addView(materialChipSetWidget)
        }

        return containerLayout
    }

    private fun updateChipsetWidgetStatus(position: Int, title: String?, selected: Boolean) {
        if (selected)
            chipSetWidgetList[position].background = getSelectedShapeBackground(context)
        else
            chipSetWidgetList[position].background = getShapeBackground(context)

        chipSetDataList?.get(position)?.isSelected = selected
        updateCloseButtonVisibility()
    }

    private fun updateCloseButtonVisibility() {

        chipSetDataList?.let {
            for (chipSetData in it) {
                if (chipSetData.isSelected){
                    closeButton?.visibility = View.VISIBLE
                    return
                }
            }
        }
        closeButton?.visibility = View.GONE
    }

    private fun getCloseButton(): View? {
        closeButton =  AppCompatImageView(context).apply {
            setImageResource(R.drawable.ic_baseline_close_24)
            //TODO remove this hardcoded code
            setPadding(10,10,10,10)
            val params = LayoutParams(60, 60)
            params.gravity = Gravity.CENTER_VERTICAL
            layoutParams = params
            background = getShapeBackground(context)
            visibility = View.GONE
            setOnClickListener {
              resetChipsetWidgetStatus()
            }
        }
        return closeButton
    }

    private fun resetChipsetWidgetStatus() {
        chipSetDataList?.apply {
            for (chipSetData in this) {
                chipSetData.isSelected = false
            }
        }
        chipSetWidgetList.forEach {
            it.refreshData()
        }
        closeButton?.visibility = View.GONE
    }


    private fun getShapeBackground(context: Context): Drawable {
        val tintColor = ContextCompat.getColor(context, android.R.color.transparent)
        val strokeColor = ColorStateList.valueOf(ContextCompat.getColor(context,
            android.R.color.darker_gray))
        //TODO remove this hardcoded code
        val rectangleShape = RectangleShape(radius = resources.getDimension(R.dimen.radius)).apply {
            this.backGroundTintColor = tintColor
            this.strokeWidth = 2.0f
            this.strokeColor =strokeColor
        }
        return ShapeRender(rectangleShape).getDrawable()
    }

    private fun getSelectedShapeBackground(context: Context) : Drawable {
        val tintColor = ContextCompat.getColor(context, R.color.selected_blue)
        val strokeColor = ColorStateList.valueOf(ContextCompat.getColor(context,
            R.color.selected_blue))
        //TODO remove this hardcoded code
        val rectangleShape = RectangleShape(radius = resources.getDimension(R.dimen.radius)).apply {
            this.backGroundTintColor = tintColor
            this.strokeWidth = 2.0f
            this.strokeColor =strokeColor
        }
        return ShapeRender(rectangleShape).getDrawable()
    }

}