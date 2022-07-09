package com.example.brainymerchandising.Display.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.Display.Display_Fragment
import com.example.brainymerchandising.Display.Model.Image
import com.example.brainymerchandising.Display.UI.Dialog.Afficher_Image_DIalog
import com.example.brainymerchandising.databinding.ItemImageBinding

class Image_Adapter(
    private val listener: Display_Fragment,
    activity: FragmentActivity,
    cameraLinear: LinearLayout,
    plusImageRc: LinearLayout,
    myPhotoCrRecycle: RecyclerView,
    baseAdapter: Adapter_base_Display,
) :
    RecyclerView.Adapter<ImageViewHolder>() {

    private val  cameraLinear = cameraLinear
    private val  plusImageRc = plusImageRc
    private val  myPhotoCrRecycle = myPhotoCrRecycle
    private val activityIns = activity
    private val   baseAdapter= baseAdapter
    interface ImageItemListener {
        fun onClickedImage(position: Int)
    }

    private val items = ArrayList<Image>()


    fun setItems(items: ArrayList<Image>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
        Log.d("sizeaa", items?.size.toString())}

     fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)}

    fun notify1(){
        val size: Int = items.size
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()}

    fun clear() {
        val size: Int = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding: ItemImageBinding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, listener as ImageItemListener, activityIns, parent,items , listener,
            cameraLinear,plusImageRc,myPhotoCrRecycle,baseAdapter)}

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) =
        holder.bind(items[position],position)
}

class ImageViewHolder(
    private val itemBinding: ItemImageBinding,
    private val listener: Image_Adapter.ImageItemListener,
    private val activityIns: FragmentActivity,
    private var parent: ViewGroup,
    items: ArrayList<Image>,
    display_fragment: Display_Fragment,
    cameraLinear: LinearLayout,
    plusImageRc: LinearLayout,
    myPhotoCrRecycle: RecyclerView,
    baseAdapter: Adapter_base_Display,
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {



    private  var display_fragment = display_fragment
    private  var cameraLinear = cameraLinear
    private  var plusImageRc = plusImageRc
    private  var items= items


    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Image,position: Int) {

        itemBinding.myimage.setImageBitmap(item.url)


        //myPhotoCrRecycle.adapter = adapterImage

       itemBinding.myimage.setOnClickListener(View.OnClickListener {
           Log.d("whenimageclicked", items?.toString())


               Afficher_Image_DIalog(
                   position,
                   (display_fragment.activity as PrimeActivity).tab_Image!!,
                   cameraLinear,
                   plusImageRc,
                   display_fragment,
                   0
           ).show(activityIns.supportFragmentManager, "afficherimage")


       })






    }


    override fun onClick(v: View?) {
        listener.onClickedImage(adapterPosition)
    }



}
