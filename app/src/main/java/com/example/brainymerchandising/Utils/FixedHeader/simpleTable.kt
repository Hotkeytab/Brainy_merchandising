package com.example.brainymerchandising.Utils.FixedHeader

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.brainymerchandising.R

class SimpleTable : Activity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table)
        val tableFixHeaders: TableFixHeaders = findViewById<View>(R.id.tableHisto) as TableFixHeaders
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
