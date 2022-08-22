package com.example.brainymerchandising.Utils.FixedHeader

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.brainymerchandising.Display.Model.Display_category
import com.example.brainymerchandising.R

class SimpleTable : Activity() {
    private var liste_Category_display = ArrayList<ArrayList<String>>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table)
        val tableFixHeaders: TableFixHeaders = findViewById<View>(R.id.tableHisto) as TableFixHeaders
        liste_Category_display[0].add("Maher1")
        liste_Category_display[0].add("Maher2")
        liste_Category_display[0].add("Maher3")
        liste_Category_display[0].add("Maher4")
        liste_Category_display[0].add("Maher5")
        liste_Category_display = arrayListOf()
        val matrixTableAdapter = MatrixTableAdapter(
            this, arrayOf(
                arrayOf(
                    "fefew",
                    "Header 2",
                    "Header 3",
                    "Header 4",
                    "Header 5",
                    "Header 6"
                ), arrayOf(
                    "Lorem",
                    "sed",
                    "do",
                    "eiusmod",
                    "tempor",
                    "incididunt"
                ), arrayOf(
                    "ipsum",
                    "irure",
                    "occaecat",
                    "enim",
                    "laborum",
                    "reprehenderit"
                ), arrayOf(
                    "dolor",
                    "fugiat",
                    "nulla",
                    "reprehenderit",
                    "laborum",
                    "consequat"
                ), arrayOf(
                    "sit",
                    "consequat",
                    "laborum",
                    "fugiat",
                    "eiusmod",
                    "enim"
                ), arrayOf(
                    "amet",
                    "nulla",
                    "Excepteur",
                    "voluptate",
                    "occaecat",
                    "et"
                ), arrayOf(
                    "consectetur",
                    "occaecat",
                    "fugiat",
                    "dolore",
                    "consequat",
                    "eiusmod"
                ), arrayOf(
                    "adipisicing",
                    "fugiat",
                    "Excepteur",
                    "occaecat",
                    "fugiat",
                    "laborum"
                ), arrayOf(
                    "elit",
                    "voluptate",
                    "reprehenderit",
                    "Excepteur",
                    "fugiat",
                    "nulla"
                )
            )
        )
        tableFixHeaders.setAdapter(matrixTableAdapter)
    }
}
