package com.justin.materialchipsetwidget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat


class MaterialChipSetWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    var chipSetWidgetListener : ChipSetWidgetListener? = null

    var chipDataSet : ChipSetData? = null
        set(value) {
            field = value
            val list = arrayListOf<ChipData>()
            value?.chipList?.forEach {
                list.add(ChipData(it))
            }
            chipDataList = list
        }

    private var chipDataList : List<ChipData>? = null
        set(value) {
            field = value
            renderContents(value)
        }

    var textColorUnselected: Int = ContextCompat.getColor(context, R.color.light_gray)
    var textColorSelected: Int = ContextCompat.getColor(context, R.color.light_gray)
    var dividerColorUnselected = ContextCompat.getColor(context, R.color.light_gray)


    init {
        this.layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT,WRAP_CONTENT)
        this.orientation = HORIZONTAL
        renderContents(chipDataList)
    }


    private fun renderContents(dataSet: List<ChipData>?) {
        dataSet?.let {
            val chipData : ChipData? = getSelectedChip(dataSet)

            if (chipData == null) {
                renderChipList(dataSet)
            } else {
                renderChipList( arrayListOf(chipData))
            }
        }
    }

    private fun getSelectedChip(dataSet: List<ChipData>): ChipData? {
        dataSet.let {
            var chipData : ChipData? = null
            for(content in it){
                if (content.isSelected) {
                    chipData = content
                    break
                } else {
                    chipData = null
                }
            }
            return chipData
        }
    }


    private fun renderChipList(dataSet: List<ChipData>) {
        for( i in dataSet.indices ) {
            val textView = TextView(context).apply {
                text = dataSet[i].title
                gravity = Gravity.CENTER
                setTextColor(textColorUnselected)
                setPadding(
                    resources.getDimension(R.dimen.chipTextStartPadding).toInt(),
                    resources.getDimension(R.dimen.chipTextTopPadding).toInt(),
                    resources.getDimension(R.dimen.chipTextEndPadding).toInt(),
                    resources.getDimension(R.dimen.chipTextBottomPadding).toInt(),
                )
                getForegroundRipple(this)
                isClickable = true
                setOnClickListener {
                    updateChipStatus(this.text)
                }
            }
            addView(textView)


            if ( i <dataSet.size-1){
                val view = View(context).apply {
                    setBackgroundColor(dividerColorUnselected)
                    layoutParams =
                        ViewGroup.LayoutParams(
                            resources.getDimension(R.dimen.chipDividerWidth).toInt(),
                            resources.getDimension(R.dimen.chipDividerHeight).toInt(),
                        )
                    gravity = Gravity.CENTER_VERTICAL
                }
                addView(view)
            }
        }
    }

    private fun updateChipStatus(title: CharSequence?) {
        title?.let { value ->
            chipDataList?.apply {
                for (i in this.indices) {
                    this[i].apply {
                        if (this.title == value ) {
                            this.isSelected = !this.isSelected
                            chipDataSet?.let {
                                chipSetWidgetListener?.onChipClicked(it.position,this.title,this.isSelected)
                            }
                        } else {
                            this.isSelected = false
                        }
                    }
                }
            }

            updateVisibilityOfChildren(chipDataList)
        }
    }

    private fun updateVisibilityOfChildren(chipDataList: List<ChipData>?) {
        val chipData: ChipData? = chipDataList?.let { getSelectedChip(it) }
        chipData?.let {
            for (i in 0 until this.childCount) {
                val child = this.getChildAt(i)
                if (child is TextView) {
                    if (child.text == it.title) {
                        child.visibility = View.VISIBLE
                        child.setTextColor(textColorSelected)
                    } else {
                        child.visibility = View.GONE
                    }
                } else {
                    child.visibility = View.GONE
                }
            }
        } ?: also {
            for (i in 0 until this.childCount) {
                val child = this.getChildAt(i)
                child.visibility = View.VISIBLE
                if (child is TextView)
                    child.setTextColor(textColorUnselected)
            }
        }
    }

    private fun getForegroundRipple(textView: TextView){
        val outValue = TypedValue()
        context.theme.resolveAttribute(R.attr.selectableItemBackgroundBorderless, outValue, true)
        textView.setBackgroundResource(outValue.resourceId)
    }

    fun refreshData() {
        chipDataList?.apply {
            for (chipData in this) {
                chipData.apply {
                        this.isSelected = false
                }
            }
        }
        updateVisibilityOfChildren(chipDataList)
    }

}