<h1 align="center">MaterialChipSetWidget</h1></br>
<p align="center">
A MaterialChipSetWidget is used to hold multiple chipsets U+1F929 and each chipset holds multiple values.
</p>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p> <br>

<p align="center">
<img src="/demo/header_image.png" />
</p>

## Including in your project 
[![](https://jitpack.io/v/JustinGeorgeJoseph/MaterialChipsetWidget.svg)](https://jitpack.io/#JustinGeorgeJoseph/MaterialChipsetWidget)

### Gradle 
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation 'com.github.JustinGeorgeJoseph:MaterialChipsetWidget:v1.0.1'
}
```

## Usage
### ColorPickerView in XML layout
We can use `MaterialChipSetsContainer` without any customized attributes.<br>
Initally there won't be any content you have add those contents dynamically.
```
    <com.justin.materialchipsetwidget.MaterialChipSetsContainer
        android:id="@+id/materialChipSetsContainer"
        android:layout_width="match_parent"
        android:scrollbars="none"
        android:layout_height="wrap_content"/>
  ```      
 
 ### Attribute descriptions
 
```
android:scrollbars="none" // sets whether a horizontal scrollbar is required or not.
```
### Initalize MaterialChipsetWidget with chips
#### Create a list of data that should be shown in the widget
```
    private fun createDataSet(): List<List<String>> {
        val arrayTime = arrayListOf("Recent", "2000s", "90s", "Old")
        val arrayPurchaseMode = arrayListOf("Paid", "Rent")
        val arrayGenre = arrayListOf("Action", "Comedy", "Sci-Fi", "Science", "Drama")
        val arrayRating = arrayListOf("Award-winning", "Highly rated")
        return arrayListOf(arrayPurchaseMode, arrayTime, arrayGenre, arrayRating)
    }
```
#### Initialize the widget with colors and dataset
```
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
        }
```

### MaterialChipsetWidgetListener
```
MaterialChipsetWidgetListener {

                override fun onChipSelectionChanged(
                    name: String?,
                    chipSelectionGroup: String,
                    checked: Boolean,
                ) {
                    
                }

                override fun onChipsetsResetClicked() {
                   
                }

            }
```


# License
```xml
Copyright 2022 JustinGeorgeJoseph (Justin George)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
