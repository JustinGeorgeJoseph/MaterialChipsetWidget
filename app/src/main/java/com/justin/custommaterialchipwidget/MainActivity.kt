package com.justin.custommaterialchipwidget

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.justin.materialchipsetwidget.MaterialChipSetsContainer
import com.justin.materialchipsetwidget.MaterialChipsetWidgetListener
import com.justin.materialchipsetwidget.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<MaterialChipSetsContainer>(R.id.materialChipSetsContainer).apply {

            chipSetSolidColorUnselected = ContextCompat.getColor(context, android.R.color.black)
            chipSetSolidColorSelected = ContextCompat.getColor(context, R.color.green)
            chipSetStrokeWidth = Utils.convertDpToPx(this@MainActivity, 3)
            chipSetStrokeColorUnselected =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))
            chipSetStrokeColorSelected =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))

            chipTextColorUnselected = ContextCompat.getColor(context, android.R.color.white)
            chipTextColorSelected = ContextCompat.getColor(context, android.R.color.white)
            chipTextDividerColorUnselected =
                ContextCompat.getColor(context, R.color.green)

            dataSet = createDataSet()

            listener = object: MaterialChipsetWidgetListener {

                override fun onChipSelectionChanged(
                    name: String?,
                    chipSelectionGroup: String,
                    checked: Boolean,
                ) {
                    Toast.makeText(this@MainActivity,"onChipSelectionChanged --> $name || $checked",Toast.LENGTH_SHORT).show()

                }

                override fun onChipsetsResetClicked() {
                    Toast.makeText(this@MainActivity,"onChipsetsResetClicked",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun createDataSet(): List<List<String>> {
        val arrayTime = arrayListOf("Recent", "2000s", "90s", "Old")
        val arrayPurchaseMode = arrayListOf("Paid", "Rent")
        val arrayGenre = arrayListOf("Action", "Comedy", "Sci-Fi", "Science", "Drama")
        val arrayRating = arrayListOf("Award-winning", "Highly rated")
        return arrayListOf(arrayPurchaseMode, arrayTime, arrayGenre, arrayRating)
    }

}