package com.example.brainymerchandising.Visite.suivie.UI.DisplaysUI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.brainymerchandising.databinding.ItemImageBinding

class AdapterImageDisplay (

) :
    RecyclerView.Adapter<ImageViewHolder>() {


    interface ImageItemListener {
        fun onClickedImage(position: Int)
    }


    private val items = ArrayList<String>()


    fun setItems(items: ArrayList<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)}





    fun clear() {
        val size: Int = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding: ItemImageBinding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)}

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) =
        holder.bind(items[position],position)
}

class ImageViewHolder(
    private val itemBinding: ItemImageBinding,

) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {



    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: String, position: Int) {





        Glide.with(itemBinding.root)
            .load(item)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(itemBinding.myimage)
        //myPhotoCrRecycle.adapter = adapterImage







    }


    override fun onClick(v: View?) {
    }



}
