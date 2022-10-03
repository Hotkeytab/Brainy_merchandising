package com.example.brainymerchandising.Visite.suivie.UI.DisplaysUI.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Display.Display_Fragment
import com.example.brainymerchandising.Display.Model.DisplayCustomFields
import com.example.brainymerchandising.Display.Model.Post.CustomFieldValue
import com.example.brainymerchandising.Visite.suivie.model.display.DisplayCustomFieldValues
import com.example.brainymerchandising.databinding.DropdownItemBinding
import com.example.brainymerchandising.databinding.ItemDisplaysuivieTextBinding

class AdapterTextViewDisplay (
    liste_display: ArrayList<DisplayCustomFieldValues>,

) : RecyclerView.Adapter<edittext_ViewHolder>() {
    private val liste_display_Recycle_adapter = liste_display




    private val items = ArrayList<DisplayCustomFieldValues>()
    private val items_Edittext_values = ArrayList<DisplayCustomFieldValues>()


    fun setItems(items: ArrayList<DisplayCustomFieldValues>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }



    fun clear() {
        val size: Int = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): edittext_ViewHolder {
        val binding: ItemDisplaysuivieTextBinding =
            ItemDisplaysuivieTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return edittext_ViewHolder(
            binding,
            liste_display_Recycle_adapter)}

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: edittext_ViewHolder, position: Int) {
        holder.bind(items[position])}
}

class edittext_ViewHolder(
    private val itemBinding: ItemDisplaysuivieTextBinding,
    private var parent: ArrayList<DisplayCustomFieldValues>,


    ) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {


    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: DisplayCustomFieldValues) {
        itemBinding.bodyValue.text = item.value
        itemBinding.title.text = item.displayCustomField!!.name





    }

    override fun onClick(v: View?) {
        val a=0    }


}
