package com.example.brainymerchandising.Display.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.Display.Display_Fragment
import com.example.brainymerchandising.Display.Model.DisplayCustomFields
import com.example.brainymerchandising.Display.Model.DisplaySections
import com.example.brainymerchandising.Display.UI.Dialog.Choix_Image
import com.example.brainymerchandising.databinding.ItemDisplayBinding


class Adapter_base_Display(private val listener: Display_Fragment,
                           activity: FragmentActivity ,
                           liste_display : ArrayList<DisplaySections>)
    : RecyclerView.Adapter<Base_DisplayViewHolder>() {
    private val activityIns = activity
    private val liste_display_Recycle_adapter = liste_display


    interface Base_DisplayListener {
        fun onClickeddisplay(position: Int)
    }

    private val items = ArrayList<DisplaySections>()



    fun setItems(items: ArrayList<DisplaySections>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged() }

    fun clear() {
        val size: Int = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()}

    fun notihy_display_adapter(){
        notifyDataSetChanged()}




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Base_DisplayViewHolder {

        val binding: ItemDisplayBinding = ItemDisplayBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Base_DisplayViewHolder(
            binding,
            listener as Base_DisplayListener,
            activityIns,
            parent,
            liste_display_Recycle_adapter,
        listener)}

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: Base_DisplayViewHolder, position: Int) {
        holder.bind(items[position])
    }
    }
class Base_DisplayViewHolder (
    private val itemBinding: ItemDisplayBinding,
    private val listener: Adapter_base_Display.Base_DisplayListener,
    private val activityIns: FragmentActivity,
    private var parent: ViewGroup,
    private val items: ArrayList<DisplaySections>,
    private var displayFragment: Display_Fragment,


    ) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {
    lateinit var adapter_childText: edittext_adapter
    private lateinit var adapterImage: Image_Adapter
    private lateinit var baseAdapter: Adapter_base_Display

    init {
        itemBinding.root.setOnClickListener(this) }

    fun bind(item: DisplaySections) {
        itemBinding.textView2.text = item.name
        adapter_childText =
            edittext_adapter(item.displayCustomFields as ArrayList<DisplayCustomFields>,item.displayTypeId,displayFragment)
        itemBinding.caseTextRecycler.isMotionEventSplittingEnabled = false
        itemBinding.caseTextRecycler.layoutManager = LinearLayoutManager(parent.context)
        itemBinding.caseTextRecycler.layoutManager = LinearLayoutManager(
            parent.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        if (!item.displayCustomFields.isEmpty())
            adapter_childText.setItems(item.displayCustomFields as ArrayList<DisplayCustomFields>)
        itemBinding.caseTextRecycler.adapter = adapter_childText


        baseAdapter = Adapter_base_Display(displayFragment,displayFragment.requireActivity(),items)

        adapterImage = Image_Adapter(displayFragment, displayFragment.requireActivity(),
            itemBinding.cameraLinear, itemBinding.plusImageRc, itemBinding.myPhotoCrRecycle,baseAdapter
        )


        itemBinding.myPhotoCrRecycle.isMotionEventSplittingEnabled = false
        itemBinding.myPhotoCrRecycle.layoutManager = LinearLayoutManager(parent.context)
        itemBinding.myPhotoCrRecycle.layoutManager = LinearLayoutManager(
            parent.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        itemBinding.myPhotoCrRecycle.adapter = adapterImage

        (displayFragment.activity as PrimeActivity).adapterImage=adapterImage







        itemBinding.addphoto.setOnClickListener {
            Log.d("primeArrayOnclickaddphoto",(displayFragment.activity as PrimeActivity).tab_Image!!.size.toString())

            Choix_Image(
                itemBinding.showImageCr,
                adapterImage,
                (displayFragment.activity as PrimeActivity).tab_Image,
                itemBinding.cameraLinear,
                itemBinding.plusImageRc,
                itemBinding.myPhotoCrRecycle,
                displayFragment
            ).show(activityIns.supportFragmentManager, "ChoixImageNewCR")
        }


        itemBinding.addphotoRcRecycle.setOnClickListener {

            Log.d("primeArrayOnclickaddphotoRcycle",
                (displayFragment.activity as PrimeActivity).tab_Image!!.size.toString())

            Choix_Image(
                itemBinding.showImageCr,
                adapterImage,
                (displayFragment.activity as PrimeActivity).tab_Image,

                itemBinding.cameraLinear,
                itemBinding.plusImageRc,
                itemBinding.myPhotoCrRecycle,
                displayFragment,
            ).show(activityIns.supportFragmentManager, "ChoixImageNewCR")
        }











}

    override fun onClick(v: View?) {
        listener.onClickeddisplay(adapterPosition)}




}
