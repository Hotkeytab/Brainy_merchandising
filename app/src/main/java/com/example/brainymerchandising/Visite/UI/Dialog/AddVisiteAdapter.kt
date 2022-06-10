package com.example.brainymerchandising.Visite.UI.Dialog

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Visite.Store.Model.Store
import com.example.brainymerchandising.Visite.visite.Model.Visite
import com.example.brainymerchandising.databinding.ItemMagasinBinding
import dagger.hilt.android.AndroidEntryPoint


class AddVisiteAdapter(
    private val listener: Add_visite_dialog //listaTasks2


):RecyclerView.Adapter<TaskViewHolder>() {


    interface Add_Store_Listener {
        fun onClicked_Store(taskId: Int,lat:Double?,lng:Double?,name: String,store: Store)
    }

    private val listestore = ArrayList<Store>()

    fun setItems(store: ArrayList<Store>) {
        this.listestore.clear()
        this.listestore.addAll(store)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding: ItemMagasinBinding =
            ItemMagasinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(
            binding,
            listener as Add_Store_Listener,
            parent,
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(listestore[position])    }

    override fun getItemCount(): Int = listestore.size


}

class TaskViewHolder(
    private val itemBinding: ItemMagasinBinding,
    private val listener: AddVisiteAdapter.Add_Store_Listener,
    private val parent: ViewGroup,
): RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {


    private lateinit var visiteResponse: Store
    lateinit var sharedPref: SharedPreferences

    init {
        itemBinding.root.setOnClickListener(this)
    }


    fun bind(store: Store) {
        this.visiteResponse = store


        //Store Picture
        if( store.path.isNullOrBlank())
            Glide.with(itemBinding.root)
                .load(store.path)   //store picture
                .placeholder(R.drawable.carrefourlogo)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(itemBinding.imageView2)

        else
            Glide.with(itemBinding.root)
                .load(R.drawable.outline_storefront_24)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(itemBinding.imageView2)


        itemBinding.name.text = store.name
        itemBinding.place.text = store.governorate
        itemBinding.distance.text = store.address



    }


    override fun onClick(v: View?) {
        listener.onClicked_Store(
            visiteResponse.id,
            visiteResponse.lat,
            visiteResponse.lng,
            visiteResponse.name,
            visiteResponse

        )

    }


}