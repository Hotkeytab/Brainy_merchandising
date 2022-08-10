package com.example.brainymerchandising.Product.Adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Product.Model.POST.productPost
import com.example.brainymerchandising.Product.Model.ProductRef
import com.example.brainymerchandising.Product.Model.StockSetting
import com.example.brainymerchandising.Product.UI.Dialog.SetAmount_product
import com.example.brainymerchandising.Product.UI.ProductFragment
import com.example.brainymerchandising.databinding.ItemProductBinding
import kotlinx.android.synthetic.main.item_product.view.*


class adapter_Product_base(
    private val listener: ProductFragment,
    activity: FragmentActivity,
    liste_display: ArrayList<ProductRef>,
    amount: TextView,
    requireActivity: FragmentActivity,
    liste_SockSetting: ArrayList<StockSetting>,
    sendStockOut: ImageView,
    listeProductRecycle: RecyclerView,
    VisitId: Int,
)
    : RecyclerView.Adapter<ProductViewHolder>() {
    private val activityIns = activity
    private val liste_display_Recycle_adapter = liste_display
    private val amount=amount
    private val liste_SockSetting=liste_SockSetting
    private val requireActivity=requireActivity
    private var isSelected : Boolean = false
    private var sendStockOut  = sendStockOut
    private var listeProductRecycle  = listeProductRecycle
    private var VisitId  = VisitId
    interface Base_ProductListener {
        fun onClickedProduct(position: Int)
    }

    private val items = ArrayList<ProductRef>()



    fun setItems(items: ArrayList<ProductRef>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged() }

    fun clear() {
        val size: Int = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()}



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding: ItemProductBinding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)



        return ProductViewHolder(
            binding,
            listener as Base_ProductListener,
            amount,requireActivity,liste_SockSetting,items,sendStockOut,listeProductRecycle,VisitId)}

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])}
}

class ProductViewHolder(
    private val itemBinding: ItemProductBinding,
    private val listener: adapter_Product_base.Base_ProductListener,

    amount: TextView,
    private val activityIns: FragmentActivity,
    liste_SockSetting: ArrayList<StockSetting>,
    items: ArrayList<ProductRef>,
    sendStockOut: ImageView,
    listeProductRecycle: RecyclerView,
    VisitId: Int,


    ): RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    val amount = amount
    val items = items
    val liste_SockSetting = liste_SockSetting
    val sendStockOut = sendStockOut
    val listeProductRecycle = listeProductRecycle
    val VisitId = VisitId
    private var isSelected : Boolean = false
    private var liste_product_StockOut = ArrayList<productPost>()
    private lateinit var ProductPost :productPost



    fun bind(item: ProductRef){
          val rep : Boolean?
          rep=true





   sendStockOut.setOnClickListener {
    for (i in 0..items.size-1){
    if(listeProductRecycle.findViewHolderForAdapterPosition(i)!!.itemView.checkBox.isChecked){
        ProductPost = productPost(
         items.get(i).product,
        items.get(i).store,
         items.get(i).productId,
        items.get(i).storeId,
        true,
        null,
            VisitId)


        liste_product_StockOut.add(ProductPost)


    }
}
       for (i in liste_product_StockOut)
           Log.d("helloo", i.toString())

       SetAmount_product(position,item,liste_product_StockOut,VisitId,"StockOut").show(activityIns.supportFragmentManager, "StockOut")

   }



   itemBinding.product.setOnClickListener {

       if(liste_SockSetting.get(0).stockManagement.equals("StockOut")){
           if (itemView.checkBox.isChecked){

               itemView.setBackgroundColor(Color.TRANSPARENT)
               itemView.checkBox.setChecked(false)
           }else{ 
               itemView.setBackgroundColor(Color.YELLOW)
               itemView.checkBox.setChecked(true)
           } } else{
               SetAmount_product(position, item, liste_product_StockOut, VisitId,"updateProduct").show(activityIns.supportFragmentManager, "updateProduct")
           }}


        itemBinding.Numero.text = item.product.barcode
        itemBinding.libele.text = item.product.label
        itemBinding.codebar.text = item.product.barcode



    }

    init {
        itemBinding.root.setOnClickListener(this) }
    override fun onClick(v: View?) {
        listener.onClickedProduct(adapterPosition)

    }






    }
