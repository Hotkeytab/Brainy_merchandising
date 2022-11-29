package com.example.brainymerchandising.Utils.FixedHeader.Adapters

import android.content.Context
import android.util.Log
import com.example.brainymerchandising.Product.Model.HistoryProductFilter
import com.example.brainymerchandising.Product.Model.Product
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Utils.Calendar.UI.VerticalWeekCalendar.m
import java.util.*


class MyAdapter(context: Context) : SampleTableAdapter(context) {
    private val width: Int
    private var height: Int
    private  var index = 0
    private  var hashMap : kotlin.collections.HashMap<String, ArrayList<HistoryProductFilter>> = kotlin.collections.HashMap<String, ArrayList<HistoryProductFilter>> ()
    private  var pi : Int = 0
    private lateinit var lab : String
    var l : ArrayList<HistoryProductFilter> = ArrayList<HistoryProductFilter>()
    var key : ArrayList<String> = ArrayList<String>()
    private var listeProduit :ArrayList<Product>? = ArrayList<Product>()


    var matrix = Array(70) {Array(70) {""} }

    fun inflateHistoTable(){
        var periodlist : ArrayList<HistoryProductFilter> = ArrayList<HistoryProductFilter>()
        for(i in listeProduit!!){
            for (j in i.stocks) {
                if (j.stockOut) {
                    if ((hashMap.containsKey(j.createdAt.substring(0,10))) &&
                            (!hashMap.getValue(j.createdAt.substring(0,10))
                                .contains(HistoryProductFilter(i.label,i.id)))){
                         l= hashMap.get(j.createdAt.substring(0,10))!!

                         l.add(HistoryProductFilter(i.label,i.id))
                            hashMap.put(j.createdAt.substring(0,10), array(HistoryProductFilter(i.label,i.id),l)!!)
                         l.clear()
                    }else {
                           hashMap.putIfAbsent(j.createdAt.substring(0,10), array(HistoryProductFilter(i.label,i.id))!!
                           )}}}}}


    fun array(category: HistoryProductFilter , list : ArrayList<HistoryProductFilter>): ArrayList<HistoryProductFilter>? {
        val gradeCategory: ArrayList<HistoryProductFilter> = ArrayList<HistoryProductFilter>()
        
        gradeCategory.addAll(list)
        gradeCategory.add(category)
        return gradeCategory
    }
    fun array(category: HistoryProductFilter ): ArrayList<HistoryProductFilter>? {
        val gradeCategory: ArrayList<HistoryProductFilter> = ArrayList<HistoryProductFilter>()
        gradeCategory.add(category)
        return gradeCategory
    }


    fun setItems(items: ArrayList<Product>) {
        listeProduit = items
    inflateHistoTable()
        keyset(hashMap)
    key.sort()

        Log.d("mahermaptestI",hashMap.toString())}

     fun getItems() :Int {
return listeProduit!!.size
    }

    override fun  getRowCount(): Int {
        return listeProduit!!.size; }

    override fun getColumnCount(): Int {
        return hashMap.size}

    override  fun getWidth(column: Int): Int {
        return width}

    override fun getHeight(row: Int): Int {
        return height}

     fun keyset(keys: kotlin.collections.HashMap<String, ArrayList<HistoryProductFilter>>){
         var Ic= 0
         var Jc= -1

         for(i in keys){
          key.add(i.key)}
         key.sort()

     for (i in listeProduit!!){

         for ( k in key){
             Jc++

             for(j in hashMap.get(k)!!){
            // Log.d("mahermaptestI",i.label.toString())
             //Log.d("mahermaptestJ",j.label.toString())

           //  Log.d("mahermaptestI",key.get(Ic))

             // Log.d("mahermaptestI",Ic.toString())



             if( i.label.equals(j.label)){

                  matrix[Ic][Jc] = "Repture"
               }


         }}
         Ic++
         Jc= -1


     }




     }


    override fun getCellString(row: Int, column: Int): String {
        var p : HistoryProductFilter
        return if ((column == -1)&&(row ==-1)) {
            "Label"}

        else if ((column > -1)&&(row == -1)) {
            key.get(column) }


     else if ((column == -1)&&(row >-1)) {
            listeProduit!!.get(row).label
        }
        else if ((column > -1)&&(row >-1)) {
           // matrix[row][column]
            ""
        }


        else{ "Maher ($row, $column)"}
        index++ }


    override fun getLayoutResource(row: Int, column: Int): Int {
        val layoutResource: Int
        when (getItemViewType(row, column)) {

            0 -> layoutResource = R.layout.item_table_family
            1 -> layoutResource = R.layout.item_table1
            2 -> layoutResource = R.layout.itemtable2
            else -> throw RuntimeException("wtf?")
        }
        return layoutResource
    }

    override  fun getItemViewType(row: Int, column: Int): Int {
        return if ((row < 0)|| (column <0)) {
            0
        } else if (matrix[row][column].equals("Repture")) {
            2

        } else  {
            1
        }
    }

    override fun getViewTypeCount(): Int {
        return 3;
    }

    init {
        val resources = context.resources
        width = resources.getDimensionPixelSize(R.dimen.table_width)
        height = resources.getDimensionPixelSize(R.dimen.table_height)
    }
}