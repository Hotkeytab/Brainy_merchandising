package com.example.brainymerchandising.Visite.suivie.UI.DisplaysUI.adapters

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Display.Adapter.Adapter_base_Display
import com.example.brainymerchandising.Display.Adapter.Image_Adapter
import com.example.brainymerchandising.Display.Adapter.edittext_adapter
import com.example.brainymerchandising.Display.UI.Dialog.Choix_Image
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Visite.suivie.UI.DisplaysUI.DisplaysSuivie
import com.example.brainymerchandising.Visite.suivie.model.VisiteSuivie
import com.example.brainymerchandising.Visite.suivie.model.display.DisplaySections
import com.example.brainymerchandising.Visite.suivie.model.display.DisplayType
import com.example.brainymerchandising.Visite.suivie.model.display.Displays
import com.example.brainymerchandising.databinding.ItemDisplayBinding
import com.example.brainymerchandising.databinding.ItemDisplayListBinding

class AdapterSuivieDisplay(
    private val listener: DisplaysSuivie,
    activity: FragmentActivity,
    liste_display: VisiteSuivie,
    navController: NavController,

    ) : RecyclerView.Adapter<Suivie_DisplayViewHolder>() {
        private val activityIns = activity
        private val liste_display_Recycle_adapter = liste_display
        private val nav = navController


        interface Suivie_DisplayListener {
            fun onClickeddisplay(position: Int)
        }

        private val items = ArrayList<Displays>()


        fun setItems(items: ArrayList<Displays>) {
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


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Suivie_DisplayViewHolder {

            val binding: ItemDisplayListBinding =
                ItemDisplayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return Suivie_DisplayViewHolder(
                binding,
                listener as Suivie_DisplayListener,
                activityIns,
                parent,
                liste_display_Recycle_adapter,
                listener,nav
            )
        }

        override fun getItemCount(): Int = items.size
        override fun onBindViewHolder(holder: Suivie_DisplayViewHolder, position: Int) {
            holder.bind(items[position])
        }
    }

    class Suivie_DisplayViewHolder(
        private val itemBinding: ItemDisplayListBinding,
        private val listener: AdapterSuivieDisplay.Suivie_DisplayListener,
        private val activityIns: FragmentActivity,
        private var parent: ViewGroup,
        private val items: VisiteSuivie,
        private var displayFragment: DisplaysSuivie,
        private var nav: NavController,


        ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        lateinit var adapter_childText: edittext_adapter
        private lateinit var adapterImage: Image_Adapter
        private lateinit var adapterImage1: Image_Adapter
        private lateinit var baseAdapter: Adapter_base_Display
        private lateinit var displayType: ArrayList<DisplayType>
        lateinit var sharedPref: SharedPreferences


        init {
            itemBinding.root.setOnClickListener(this)
        }


        fun bind(item: Displays) {
            itemBinding.nomDisp.text = item.displayType!!.name
            itemBinding.heureDisplay.text = item.displayType!!.updatedAt
        }




        override fun onClick(v: View?) {
            listener.onClickeddisplay(adapterPosition)
            val bundle = Bundle()
            bundle.putSerializable("productList", items)
            bundle.putInt("DispID", adapterPosition)
            nav.navigate(R.id.action_displaysSuivie_to_displaySuivieDetails,bundle) }


    }
