package com.example.brainymerchandising.Visite.UI.Dialog.CheckIn

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Utils.resources.ConstModele.SuccessResponse
import com.example.brainymerchandising.Utils.resources.Resource
import com.example.brainymerchandising.Utils.resources.Server_date.Model.Get_date_Model
import com.example.brainymerchandising.Utils.resources.Server_date.ViewModel.Date_ViewModel
import com.example.brainymerchandising.Utils.resources.Server_date.ViewModel.Date_time_provider
import com.example.brainymerchandising.Visite.UI.LocationValueListener
import com.example.brainymerchandising.Visite.UI.MainVisiteAdapter
import com.example.brainymerchandising.Visite.UI.MainVisiteFragment
import com.example.brainymerchandising.Visite.visite.Model.VisitPost
import com.example.brainymerchandising.Visite.visite.Model.Visite
import com.example.brainymerchandising.Visite.visite.ViewModel.VisiteViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.will_check.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class Check_In_Dialog (
    listener_mainVisite: MainVisiteFragment,
    navController: NavController,
    etat: Int,
    view_checkIn: View,
    adapter_MainVisite: MainVisiteAdapter,
    liste_visite: ArrayList<Visite>,
    visite: Visite,


    ) :
    DialogFragment() {
    private val dateviewmodel: Date_ViewModel by viewModels()

    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 3
    private var GpsStatus = false
    private lateinit var locationIn: Location
    var veriftest = false
    val navControllerIn = navController
    var testGps = false
    val etat = etat
    val view_dialog = view_checkIn
    val adapter_MainVisite = adapter_MainVisite
    val liste_de_viste = liste_visite
    var visite = visite
    var listener = listener_mainVisite
    private lateinit var responseDate: Resource<Get_date_Model>
    private var date_now =""
    private var time_now =""
    private var dateISo =""
    private lateinit var responseTime: Resource<Get_date_Model>
    private val viewModel: VisiteViewModel by viewModels()
    //private val viewModelQuiz: MyQuizViewModel by viewModels()
    private lateinit var responseAdd: Resource<SuccessResponse>

    // declare a global variable of FusedLocationProviderClient
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var userId = 0
    lateinit var sharedPref: SharedPreferences


    interface CloseCheckDialogListener {
        fun onClosedCheckDialog()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.will_check, container, false)
    }

    override fun onStart() {
        super.onStart()

        val width = (resources.displayMetrics.widthPixels * 0.99).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.88).toInt()
        dialog!!.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog!!.window!!.setWindowAnimations(R.style.AnimationsForMapDialog)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDate()
        sharedPref = requireContext().getSharedPreferences(
            R.string.app_name.toString(),
            Context.MODE_PRIVATE
        )
        userId = sharedPref.getInt("id", 0)

        // getLocation()

        if (etat == 1) {
            title1.text = "Pointage"
            textcontext1.text = "Souhaitez vous confirmer le pointage de Début ?"
        } else if (etat == 2) {
            title1.text = "Pointage"
            textcontext1.text = "Souhaitez vous confirmer le pointage de Fin ?"
        } else if (etat == 3) {
            title1.text = "Questionnaire"
            textcontext1.text = "Souhaitez vous répondre au questionnaire ? "
        }

        accept.setOnClickListener {

            progress_indicator.visibility = View.VISIBLE
            cancel_button.isEnabled =false
            accept.isEnabled = false
            dialog!!.setCancelable(false)

            if (etat == 1) {  //pointage entree


                GlobalScope.launch(Dispatchers.Main) {
                    val visitePost = VisitPost(
                        visite.id,
                        date_now,  //dateserveur
                        0,
                        visite.storeId,
                        userId,
                        false,
                        dateISo,
                        null

                    )
                    Log.d("meher1",visitePost.toString())

                    val arayListVsitePost = ArrayList<VisitPost>()
                    arayListVsitePost.add(visitePost)
                    responseAdd = viewModel.addVisite(arayListVsitePost)

                    if (responseAdd.responseCode == 201) {
                        cancel_button.isEnabled =true
                        accept.isEnabled = true
                        dialog!!.setCancelable(true)

                        progress_indicator.visibility = View.GONE

                        val snack = Snackbar.make(
                            view,
                            "Pointage de Début envoyé avec succès",
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(resources.getColor(R.color.purpleLogin))
                        val view: View = snack.view
                        val params = view.layoutParams as FrameLayout.LayoutParams
                        params.gravity = Gravity.CENTER
                        dismiss()
                        view.layoutParams = params
                        snack.show()
                        Log.d("meher1","t3adet snac")

                        //  var targetNew = listener as CloseCheckDialogListener
                        // targetNew.onClosedCheckDialog()
                    } else {
                        cancel_button.isEnabled =true
                        accept.isEnabled = true
                        dialog!!.setCancelable(true)
                        progress_indicator.visibility = View.GONE
                        val snack = Snackbar.make(
                            view,
                            "Erreur envoie pointage",
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(resources.getColor(R.color.red))
                        val view: View = snack.view
                        val params = view.layoutParams as FrameLayout.LayoutParams
                        params.gravity = Gravity.CENTER
                        dismiss()
                        adapter_MainVisite.setVisite(liste_de_viste)
                        view.layoutParams = params
                        snack.show()
                    }


                }

            } else if (etat == 2) {
                //pointage fin


                getDate()
                GlobalScope.launch(Dispatchers.Main) {
                    val visitePost = VisitPost(
                        visite.id,
                        dateISo,
                        0,
                        visite.storeId,
                        userId,
                        false,
                        date_now,
                        date_now
                    )
                    val arayListVsitePost = ArrayList<VisitPost>()
                    arayListVsitePost.add(visitePost)
                    responseAdd = viewModel.addVisite(arayListVsitePost)

                    if (responseAdd.responseCode == 201) {
                        progress_indicator.visibility = View.GONE
                        cancel_button.isEnabled =true
                        accept.isEnabled = true
                        dialog!!.setCancelable(true)

                        val snack = Snackbar.make(
                            view,
                            "Pointage de Fin envoyé avec succès",
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(resources.getColor(R.color.purpleLogin))
                        val view: View = snack.view
                        val params = view.layoutParams as FrameLayout.LayoutParams
                        params.gravity = Gravity.CENTER
                        dismiss()
                        view.layoutParams = params
                        snack.show()
                     //   var targetNew = listener as CloseCheckDialogListener
                       // targetNew.onClosedCheckDialog()
                    } else {
                        cancel_button.isEnabled =true
                        accept.isEnabled = true
                        dialog!!.setCancelable(true)
                        progress_indicator.visibility = View.GONE
                        val snack = Snackbar.make(
                            view,
                            "Erreur envoie pointage",
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(resources.getColor(R.color.red))
                        val view: View = snack.view
                        val params = view.layoutParams as FrameLayout.LayoutParams
                        params.gravity = Gravity.CENTER
                        dismiss()
                        adapter_MainVisite.setVisite(liste_de_viste)
                        view.layoutParams = params
                        snack.show()
                    }


                }


            } else if (etat == 3) {

                cancel_button.isEnabled =true
                accept.isEnabled = true
                dialog!!.setCancelable(true)
                progress_indicator.visibility = View.GONE
                dismiss()
                LocationValueListener.locationOn = false
                navControllerIn.navigate(R.id.action_mainVisiteFragment_to_display_Fragment2)





            }


        }

        cancel_button.setOnClickListener {
            progress_indicator.visibility = View.GONE
            LocationValueListener.locationOn = true
            dismiss()
        }


    }

/*
    private fun compareDatesDay2(simpleDate: String): String {

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val date: Date = format.parse(simpleDate)
        format.applyPattern("HH:mm")
        return format.format(date)
    }

    private fun getDateNow(): String {

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val d = Date()
        return sdf.format(d)

    }

*/
private fun getDate(){
    lifecycleScope.launch(Dispatchers.Main) {
        responseDate = dateviewmodel.getDatetime()
        if(responseDate.responseCode ==200){
            val a : Date_time_provider = Date_time_provider()
            date_now = a.getDatee(responseDate.data!!.Date)
            time_now  = a.getTime(responseDate.data!!.Date)
            dateISo = responseDate.data!!.Date
            //Log.d("meher1",date_now)


        }

    }

}

}

