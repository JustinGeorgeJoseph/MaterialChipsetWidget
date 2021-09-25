package com.justin.custommaterialchipwidget

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dataSetList = createDataSet()
        val materialChipSetsContainer = findViewById<MaterialChipSetsContainer>(R.id.materialChipSetsContainer)
        materialChipSetsContainer.dataSet = dataSetList
    }

    private fun createDataSet(): List<List<String>> {
        val arrayTime = arrayListOf("Recent","2000s","90s","Old")
        val arrayPurchaseMode = arrayListOf("Paid","Rent")
        val arrayGenre = arrayListOf("Action","Comedy","Sci-Fi","Science","Drama")
        val arrayRating = arrayListOf("Award-winning","Highly rated")
        return arrayListOf(arrayPurchaseMode,arrayTime,arrayGenre,arrayRating)
    }

}