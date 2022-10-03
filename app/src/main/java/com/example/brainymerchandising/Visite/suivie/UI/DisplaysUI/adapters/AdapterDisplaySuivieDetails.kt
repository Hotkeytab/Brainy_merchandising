package com.example.brainymerchandising.Visite.suivie.UI.DisplaysUI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Display.Adapter.Adapter_base_Display
import com.example.brainymerchandising.Display.Adapter.Image_Adapter
import com.example.brainymerchandising.Display.Model.DisplayCustomFields
import com.example.brainymerchandising.Visite.suivie.UI.DisplaysUI.DisplaySuivieDetails
import com.example.brainymerchandising.Visite.suivie.model.VisiteSuivie
import com.example.brainymerchandising.Visite.suivie.model.display.DisplayCustomFieldValues
import com.example.brainymerchandising.Visite.suivie.model.display.DisplaySections
import com.example.brainymerchandising.databinding.ItemDisplayBinding

class AdapterDisplaySuivieDetails(
    private val listener: DisplaySuivieDetails,
    activity: FragmentActivity,
    visiteResponseSuivie: VisiteSuivie,
    DisplayID: Int,
) : RecyclerView.Adapter<Suivie_DisplayDetailsViewHolder>() {
    private val activityIns = activity
    private val visiteResponseSuivie = visiteResponseSuivie
    private val DisplayID = DisplayID


    interface Suivie_DisplayDetailsListener {
        fun onClickeddisplay(position: Int)
    }

    private val items = ArrayList<DisplaySections>()


    fun setItems(items: ArrayList<DisplaySections>) {
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

    fun notihy_display_adapter() {
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Suivie_DisplayDetailsViewHolder {

        val binding: ItemDisplayBinding =
            ItemDisplayBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Suivie_DisplayDetailsViewHolder(
            binding,
            listener as Suivie_DisplayDetailsListener,
            activityIns,
            parent,
            listener,visiteResponseSuivie,DisplayID
        )
    }

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: Suivie_DisplayDetailsViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class Suivie_DisplayDetailsViewHolder(
    private val itemBinding: ItemDisplayBinding,
    private val listener: AdapterDisplaySuivieDetails.Suivie_DisplayDetailsListener,
    private val activityIns: FragmentActivity,
    private var parent: ViewGroup,
    private var displayFragment: DisplaySuivieDetails,
    private var visiteResponseSuivie: VisiteSuivie,
    private var DisplayID: Int,


    ) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {
    lateinit var adapter_childText: AdapterTextViewDisplay
    private lateinit var adapterImage: Image_Adapter
    private lateinit var adapterImage1: Image_Adapter
    private lateinit var baseAdapter: Adapter_base_Display

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: DisplaySections) {

        itemBinding.addphotoRcRecycle.visibility = View.GONE
        itemBinding.textView2.text = item.name

      for(i in visiteResponseSuivie.displays[DisplayID].displayCustomFieldValues){
          if (i.displayCustomField!!.displaySectionId == item.id){


              adapter_childText =
                  AdapterTextViewDisplay(
                      visiteResponseSuivie.displays[DisplayID].displayCustomFieldValues
                              as ArrayList<DisplayCustomFieldValues>,)

              itemBinding.caseTextRecycler.isMotionEventSplittingEnabled = false
              itemBinding.caseTextRecycler.layoutManager = LinearLayoutManager(parent.context)
              itemBinding.caseTextRecycler.layoutManager = LinearLayoutManager(
                  parent.context,
                  LinearLayoutManager.VERTICAL,
                  false
              )

              adapter_childText.setItems(visiteResponseSuivie.displays[DisplayID].displayCustomFieldValues)
              itemBinding.caseTextRecycler.adapter = adapter_childText



          }

      }





    }

    override fun onClick(v: View?) {
        listener.onClickeddisplay(adapterPosition)


    }


}
