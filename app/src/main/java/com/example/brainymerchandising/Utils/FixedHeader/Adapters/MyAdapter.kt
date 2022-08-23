package com.example.brainymerchandising.Utils.FixedHeader.Adapters

import android.content.Context
import com.example.brainymerchandising.Product.Model.Product
import com.example.brainymerchandising.Product.Model.ProductRef
import com.example.brainymerchandising.R
import kotlinx.coroutines.GlobalScope

class MyAdapter(context: Context) : SampleTableAdapter(context) {
    private val width: Int
    private val height: Int
    private  var index = 0


    private  var listeProduit :ArrayList<Product>? = ArrayList<Product>()

    fun inflateHistoTable(){

    }


    fun setItems(items: ArrayList<Product>) {
        listeProduit = items

   }

     fun getItems() :Int {
return listeProduit!!.size
    }

    override fun  getRowCount(): Int {

        return listeProduit!!.size;

    }
    override fun getColumnCount(): Int {
        return 6;
    }
    override  fun getWidth(column: Int): Int {
        return width
    }

    override fun getHeight(row: Int): Int {
        return height
    }

    override   fun getCellString(row: Int, column: Int): String {
        return if ((column == -1)&&(row ==-1)) {
            "Label"
        }else if ((column == -1)&&(row >-1)) {
            listeProduit!!.get(row).label

        } else{ "Lorem ($row, $column)"}
        index++
    }

    override fun getLayoutResource(row: Int, column: Int): Int {
        val layoutResource: Int
        when (getItemViewType(row, column)) {

            0 -> layoutResource = R.layout.item_table_family
            1 -> layoutResource = R.layout.item_table1
            else -> throw RuntimeException("wtf?")
        }
        return layoutResource
    }

    override  fun getItemViewType(row: Int, column: Int): Int {
        return if ((row < 0)|| (column <0)) {
            0
        } else {
            1
        }
    }

    override fun getViewTypeCount(): Int {
        return 2;
    }

    init {
        val resources = context.resources
        width = resources.getDimensionPixelSize(R.dimen.table_width)
        height = resources.getDimensionPixelSize(R.dimen.table_height)
    }
}