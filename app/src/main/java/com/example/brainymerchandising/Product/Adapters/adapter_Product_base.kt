package com.example.brainymerchandising.Product.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Product.Model.ProductRef
import com.example.brainymerchandising.Product.UI.Dialog.SetAmount_product
import com.example.brainymerchandising.Product.UI.ProductFragment
import com.example.brainymerchandising.databinding.ItemProductBinding
import hilt_aggregated_deps._com_example_brainymerchandising_Product_UI_Dialog_SetAmount_product_GeneratedInjector


class adapter_Product_base(
    private val listener: ProductFragment,
    activity: FragmentActivity,
    liste_display: ArrayList<ProductRef>,
    amount: TextView,
    requireActivity: FragmentActivity
)
    : RecyclerView.Adapter<ProductViewHolder>() {
    private val activityIns = activity
    private val liste_display_Recycle_adapter = liste_display
    private val amount=amount
    private val requireActivity=requireActivity

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
            amount,requireActivity)}

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])}
}

class ProductViewHolder(
    private val itemBinding: ItemProductBinding,
    private val listener: adapter_Product_base.Base_ProductListener,

    amount: TextView,
    private val activityIns: FragmentActivity


    ): RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    val amount = amount
    fun bind(item: ProductRef){
          val rep : Boolean
          rep=true


        itemBinding.product.setOnClickListener {
         /*   if(itemBinding.checkBox.isChecked){
                itemBinding.quantite.visibility = View.VISIBLE
                amount.visibility = View.VISIBLE
            }else{
                itemBinding.quantite.visibility = View.GONE
                amount.visibility = View.GONE
            }  */
 Log.d("yoo","clicked")
            SetAmount_product(position,item).show(activityIns.supportFragmentManager, "updateProduct")

        }


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
