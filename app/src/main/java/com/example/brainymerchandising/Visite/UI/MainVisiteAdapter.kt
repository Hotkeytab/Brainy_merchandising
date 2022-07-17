package com.example.brainymerchandising.Visite.UI

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Visite.UI.Dialog.Position.PositionMapDialog
import com.example.brainymerchandising.Visite.visite.Model.Visite
import com.example.brainymerchandising.databinding.ItemMagasinBinding
import kotlinx.android.synthetic.main.dialog_position_map.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


class MainVisiteAdapter(
    private val listener: MainVisiteFragment,
    activity: FragmentActivity,
    activityDrawer2: PrimeActivity,
    listeVisite: ArrayList<Visite>,
    navController: NavController
) : RecyclerView.Adapter<MainVisiteViewHolder>() {
    private val activityIns = activity
    private val activityDrawer = activityDrawer2
    private val liste_visite_Recycle_adapter = listeVisite
    private val  navController= navController

    interface VisiteItemListener {
        fun onClickedVisite(taskId: Int, distance: String, visite: Visite, theDistance: Float)
        fun onClosedCheckDialog()
   }

    private val visite = ArrayList<Visite>()



    fun setVisite(visite: ArrayList<Visite>) {
        this.visite.clear()
        this.visite.addAll(visite)
        notifyDataSetChanged() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVisiteViewHolder {
        val binding: ItemMagasinBinding =
            ItemMagasinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainVisiteViewHolder(
            binding,
            listener as VisiteItemListener,
            activityIns,
            parent,
            activityDrawer,
            this,
            liste_visite_Recycle_adapter,visite,
                    navController
        )

    }

    override fun getItemCount(): Int = visite.size

    fun removeItem(position: Int) {
        visite.removeAt(position)
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position)
    }

    fun restoreItem(v: Visite?, position: Int) {
        visite.add(position, v!!)
        // notify item added by position
        notifyItemInserted(position)
    }

    override fun onBindViewHolder(holder: MainVisiteViewHolder, position: Int) =
        holder.bind(visite[position])

}

class MainVisiteViewHolder(
    private val itemBinding: ItemMagasinBinding,
    private val listener: MainVisiteAdapter.VisiteItemListener,
    private val activityIns: FragmentActivity,
    private val parent: ViewGroup,
    private val activityDrawer: PrimeActivity,
    private val taskAdapter: MainVisiteAdapter,
    private val items: ArrayList<Visite>,
    private val items2: ArrayList<Visite>,
    navController: NavController
):RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {
    private lateinit var visiteResponse: Visite
    lateinit var sharedPref: SharedPreferences
    private var finalDistance = ""
    private var theDistance = 0f
    private var  nav = navController
    init {
        itemBinding.root.setOnClickListener(this)
    }




    override fun onClick(v: View?) {
        putStoreName(visiteResponse.store.name)
        putVisiteId(visiteResponse.id)
        putUserId(visiteResponse.userId)
        listener.onClickedVisite(
            visiteResponse.storeId,
            finalDistance,
            visiteResponse,
            theDistance
        )


        if(visiteResponse.start != null && visiteResponse.end ==null ){

        if(itemBinding.MenuNavigationItem1.isVisible)
        itemBinding.MenuNavigationItem1.visibility= (View.GONE)
        else
            itemBinding.MenuNavigationItem1.visibility= (View.VISIBLE)


        }
    }

    //calculate Distance
    fun distance(lat_a: Float, lng_a: Float, lat_b: Float, lng_b: Float): Float {
        val earthRadius = 3958.75
        val latDiff = Math.toRadians((lat_b - lat_a).toDouble())
        val lngDiff = Math.toRadians((lng_b - lng_a).toDouble())
        val a = sin(latDiff / 2) * sin(latDiff / 2) +
                cos(Math.toRadians(lat_a.toDouble())) * cos(Math.toRadians(lat_b.toDouble())) *
                sin(lngDiff / 2) * sin(lngDiff / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val distance = earthRadius * c
        val meterConversion = 1609
        return (distance * meterConversion.toFloat()).toFloat()
    }
    //Put Store Name in SHaredPref
    private fun putStoreName(storeName: String) {
        sharedPref =
            parent.context.getSharedPreferences(
                R.string.app_name.toString(),
                Context.MODE_PRIVATE
            )!!
        with(sharedPref.edit()) {
            this?.putString("storeName", storeName)
        }?.commit()


    }

    private fun putVisiteId(visiteId: Int) {
        sharedPref =
            parent.context.getSharedPreferences(
                R.string.app_name.toString(),
                Context.MODE_PRIVATE
            )!!
        with(sharedPref.edit()) {
            this?.putInt("visiteId", visiteId)
        }?.commit()
    }

    private fun putUserId(UserId: Int) {
        sharedPref =
            parent.context.getSharedPreferences(
                R.string.app_name.toString(),
                Context.MODE_PRIVATE
            )!!
        with(sharedPref.edit()) {
            this?.putInt("userId", UserId)
        }?.commit()
    }


    fun bind(visite: Visite) {

        this.visiteResponse = visite



        // Mettre la CardView à jour si le poinatge s'est lancé
        if(visite.start != null){
            itemBinding.rowDebutViste.setBackgroundColor(Color.parseColor("#009688"))
            itemBinding.debut.text = "Commencé en : ${visite.start}"
            itemBinding.debut.setSelected(true)

        } else {
            itemBinding.rowDebutViste.setBackgroundColor(Color.parseColor("#DF0B0B"))
            itemBinding.debut.text = "pas encore commencé "

        }
        // Mettre la CardView à jour si le poinatge si s'est terminer

        if(visite.end != null) {
            itemBinding.rowFinViste.setBackgroundColor(Color.parseColor("#009688"))
            itemBinding.finVisite.text = "Fin en : ${visite.end}"
            itemBinding.finVisite.setSelected(true)
        } else {
            itemBinding.rowFinViste.setBackgroundColor(Color.parseColor("#DF0B0B"))
            itemBinding.finVisite.text = "pas encore Terminer" }


 // Mettre la CardView à jour du reste
        itemBinding.name.text = visite.store.name
                itemBinding.place.text = visite.store.governorate + ", " + visite.store.address
        itemBinding.place.setSelected(true)
        itemBinding.rowDebutViste.visibility= (View.VISIBLE)
        itemBinding.rowFinViste.visibility= (View.VISIBLE)
        //Calculate Distance


        theDistance = distance(
            LocationValueListener.myLocation.latitude.toFloat(),
            LocationValueListener.myLocation.longitude.toFloat(),
            visite.store.lat.toFloat(),
            visite.store.lng.toFloat()
        )


        //If Distance Good set Item Clickable
        if (theDistance > 250) {

            finalDistance = theDistance.toInt().toString() + " m"
            //itemBinding.cardviewColorEnable.setCardBackgroundColor(Color.rgb(255, 255, 255))
            itemBinding.imageView2.setOnClickListener {
                putStoreName(visite.store.name)
                putVisiteId(visite.id)
                listener.onClickedVisite(
                    visiteResponse.id,
                    finalDistance,
                    visite,
                    theDistance
                )
            }

            itemBinding.name.setOnClickListener {
                putStoreName(visite.store.name)
                putVisiteId(visite.id)
                listener.onClickedVisite(
                    visiteResponse.id,
                    finalDistance,
                    visite,
                    theDistance
                )

            }
        } else {
            finalDistance = (theDistance.toInt() / 1000).toString() + " km"

           // itemBinding.cardviewColorEnable.setCardBackgroundColor(Color.rgb(220, 220, 220))
        }

        itemBinding.distance.visibility = View.VISIBLE
        itemBinding.distance.text = finalDistance

        itemBinding.showMap.setOnClickListener {

            if (!StaticMapClicked.mapIsRunning) {
                StaticMapClicked.mapIsRunning = true

                PositionMapDialog(
                    visite.store.lat,
                    visite.store.lng,
                    visite.store.name
                ).show(activityIns.supportFragmentManager, "PositionMapDialog")}}

        itemBinding.navNewDisplay.setOnClickListener {

            nav.navigate(R.id.action_mainVisiteFragment_to_display_Fragment2) }


        itemBinding.navNewProduct.setOnClickListener {

            nav.navigate(R.id.action_mainVisiteFragment_to_productFragment) }
    }






}
