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
    plusImageRc: LinearLayout,
    myPhotoCrRecycle: RecyclerView,
    baseAdapter: Adapter_base_Display,
    SectionId: Int,
) :
    RecyclerView.Adapter<ImageViewHolder>() {

    private val  plusImageRc = plusImageRc
    private val  myPhotoCrRecycle = myPhotoCrRecycle
    private val  activityIns = activity
    private val  baseAdapter= baseAdapter
    private val  SectionId= SectionId

    interface ImageItemListener {
        fun onClickedImage(position: Int)
    }


    private val items = ArrayList<Image>()


    fun setItems(items: ArrayList<Image>) {
        this.items.clear()

        for ( i in items){
            if(i.SectionId == SectionId){
                this.items.add(i)
            }
            notifyDataSetChanged()
        }}

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
        notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding: ItemImageBinding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, listener as ImageItemListener, activityIns, parent,items , listener,
       plusImageRc,myPhotoCrRecycle,baseAdapter,SectionId)}

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
    plusImageRc: LinearLayout,
    myPhotoCrRecycle: RecyclerView,
    baseAdapter: Adapter_base_Display,
    SectionId: Int,
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {



    private  var display_fragment = display_fragment
    private  var plusImageRc = plusImageRc
    private  var items= items
    private  var baseAdapter= baseAdapter
    private  var SectionId= SectionId


    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Image,position: Int) {
        Log.d("imagearraysectionID", SectionId?.toString())

        itemBinding.myimage.setImageBitmap(item.url)


        //myPhotoCrRecycle.adapter = adapterImage
       itemBinding.myimage.setOnClickListener(View.OnClickListener {


               Afficher_Image_DIalog(
                   position,
                   (display_fragment.activity as PrimeActivity).tab_Image!!,
                   plusImageRc,
                   display_fragment,
                   0,
                   SectionId,
                   (display_fragment.activity as PrimeActivity).adapterImage).show(activityIns.supportFragmentManager, "afficherimage")



       })






    }


    override fun onClick(v: View?) {
        listener.onClickedImage(adapterPosition)
    }



}
