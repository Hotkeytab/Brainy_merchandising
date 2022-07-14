package com.example.brainymerchandising.Visite.UI

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Utils.Anim.RecyclerItemTouchHelper
import com.example.brainymerchandising.Utils.resources.Resource
import com.example.brainymerchandising.Utils.resources.Server_date.Model.Get_date_Model
import com.example.brainymerchandising.Utils.resources.Server_date.ViewModel.Date_ViewModel
import com.example.brainymerchandising.Utils.resources.Server_date.ViewModel.Date_time_provider
import com.example.brainymerchandising.Visite.UI.Dialog.Add_visite_dialog
import com.example.brainymerchandising.Visite.UI.Dialog.CheckIn.Check_In_Dialog
import com.example.brainymerchandising.Visite.visite.Model.ListVisiteGet
import com.example.brainymerchandising.Visite.visite.Model.Visite
import com.example.brainymerchandising.Visite.visite.ViewModel.VisiteViewModel
import com.example.brainymerchandising.databinding.FragmentMainVisiteBinding
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_main_visite.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@AndroidEntryPoint
class MainVisiteFragment : Fragment(), MainVisiteAdapter.VisiteItemListener {
    private lateinit var binding: FragmentMainVisiteBinding


    private val viewModel: VisiteViewModel by viewModels()
    private val dateviewmodel: Date_ViewModel by viewModels()
    private lateinit var responseData: Resource<ListVisiteGet>
    private lateinit var responseDate: Resource<Get_date_Model>
    private var lista_de_visite = ArrayList<Visite>()
    private lateinit var main_viste_adapter: MainVisiteAdapter
    private lateinit var addVisiteDialog: Add_visite_dialog
    private lateinit var dateTimeBegin: String
    private lateinit var dateTimeEnd: String
    private lateinit var navController: NavController
    private var fm: FragmentManager? = null
    lateinit var sharedPref: SharedPreferences
    private var date = ""
    private var userId = 0
    private var time = ""
    private val REQUEST_CODE = 2
    private lateinit var locationManager: LocationManager
    private var GpsStatus = false
    private lateinit var visite_interne: Visite


    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentMainVisiteBinding.inflate(inflater, container, false)

        if (isAdded && activity != null) {


            fm = childFragmentManager

            sharedPref = requireContext().getSharedPreferences(
                R.string.app_name.toString(),
                Context.MODE_PRIVATE
            )
            userId = sharedPref.getInt("id", 0)

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            //  dateTimeBegin = simpleDateFormat.format("2022-06-06").toString()
            // dateTimeEnd = simpleDateFormat.format("2022-06-06").toString()
            dateTimeBegin = "2022-06-09"
            dateTimeEnd = "2022-06-09"

        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        //If Fragment is Added and activity not null
        if (isAdded && activity != null) {
            //Get Drawer Layout instance
            val mDrawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)

            //Top Bar
            topAppBar.setNavigationOnClickListener {
                mDrawerLayout.openDrawer(Gravity.LEFT)
            }
            //Make Bottom Nav Visible
            //requireActivity().bottom_nav.visibility = View.VISIBLE
            getDate()

            getVisites()

            //init Nav Controller

            navController = NavHostFragment.findNavController(this)


            binding.AddVisiteButton.setOnClickListener {

                //Init Visite Dialog and SHow it
                addVisiteDialog = Add_visite_dialog(lista_de_visite)
                addVisiteDialog.show(fm!!, "add")
                fm!!.executePendingTransactions()

                //Add Visite Dialog onCLose Listener
                addVisiteDialog.dialog!!.setOnDismissListener {
                    getVisites()
                }


            }

        }


    }

    override fun onStart() {
        super.onStart()

        //If Fragment is Added and Activity not null
        if (isAdded && activity != null) {
            //Set Location Fused Listener
            LocationValueListener.locationOn = true
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

            //Ask Permission GPS
            askForPermissions()
        }
    }


    @DelicateCoroutinesApi
    private fun getVisites() {
        Log.d("meher1", date)

        lifecycleScope.launch(Dispatchers.Main) {
            if (!isDetached) {
                if (isAdded) {
                    responseData =
                        viewModel.getVisites(userId.toString(), date, date)
                    if (responseData.responseCode == 200) {
                        lista_de_visite = responseData.data!!.data as ArrayList<Visite>

                        if (lista_de_visite.size == 0)
                            binding.novisit.visibility = View.VISIBLE
                        else
                            binding.novisit.visibility = View.GONE

                        if (isAdded && activity != null)
                            setupRecycleViewPredictionDetail()
                        binding.progressIndicator.visibility = View.GONE
                    }
                }
            }

        }
    }

    private fun setupRecycleViewPredictionDetail() {

        main_viste_adapter = MainVisiteAdapter(
            this, requireActivity(),
            activity as PrimeActivity, lista_de_visite, navController
        )
        binding.taskRecycleview.isMotionEventSplittingEnabled = false
        binding.taskRecycleview.layoutManager = LinearLayoutManager(requireContext())
        binding.taskRecycleview.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.taskRecycleview.adapter = main_viste_adapter
        main_viste_adapter.setVisite(lista_de_visite)


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deletedCourse: Visite =
                    lista_de_visite.get(viewHolder.adapterPosition)

                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                lista_de_visite.removeAt(viewHolder.adapterPosition)

                // below line is to notify our item is removed from adapter.
                main_viste_adapter.notifyItemRemoved(viewHolder.adapterPosition)

                // below line is to display our snackbar with action.
                Snackbar.make(binding.taskRecycleview, "deletedCourse.id", Snackbar.LENGTH_LONG)
                    .setAction("Undo",
                        View.OnClickListener { // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            lista_de_visite.add(position, deletedCourse)

                            // below line is to notify item is
                            // added to our adapter class.
                            main_viste_adapter.notifyItemInserted(position)
                        }).show()
            } // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(binding.taskRecycleview)
    }


    private fun getDate() {
        lifecycleScope.launch(Dispatchers.Main) {
            responseDate = dateviewmodel.getDatetime()
            if (responseDate.responseCode == 200) {
                val a: Date_time_provider = Date_time_provider()
                Log.d("maher", a.getDatee(responseDate.data!!.Date))
                Log.d("maher", a.getTime(responseDate.data!!.Date))
                date = a.getDatee(responseDate.data!!.Date)
                time = a.getTime(responseDate.data!!.Date)


            }

        }

    }


    override fun onClickedVisite(
        taskId: Int,
        distance: String,
        visite: Visite,
        theDistance: Float
    ) {
        if (theDistance > 250) {
            visite_interne = visite

            //Ask for GPS Permission
            askForPermissionsDialog()

            //Put StoreId in sharedPref
            sharedPref =
                requireContext().getSharedPreferences(
                    R.string.app_name.toString(),
                    Context.MODE_PRIVATE
                )!!
            with(sharedPref.edit()) {
                this?.putInt("storeId", taskId)
            }?.commit()
        }

    }


    //Calculate Distance from Lat and Lng
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

    //SHow the Dialog of Ask Permissions( Like GPS)
    fun askForPermissionsDialog(): Boolean {
        if (!isPermissionsAllowed()) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showPermissionDeniedDialog()
            } else {

                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            }
            return false
        } else {
            if (CheckGpsStatus())
            // SurveyCheckDialog(latitude, Longitude,navController).show(fm, "SurveyDialog")
            {


                Log.d("maher22", visite_interne.start.toString())
                Log.d("maher22", visite_interne.end.toString())


                if (visite_interne.start == null && visite_interne.end == null) {
                    Check_In_Dialog(
                        this,
                        navController,
                        1,
                        requireView(),
                        main_viste_adapter,
                        lista_de_visite,
                        visite_interne
                    ).show(fm!!, "SurveyDialog")

                } else {
                    /*
                                      if (visite_interne.end == null)


                                          Check_In_Dialog(
                                              this,
                                              navController,
                                              2,
                                              requireView(),
                                              main_viste_adapter,
                                              lista_de_visite,
                                              visite_interne
                                          ).show(fm!!, "SurveyDialog")

                  */
                }

            } else {
                showPermissionDeniedGPS()
            }
        }
        return true
    }


    private fun setUpLocationListener() {

        if (lista_de_visite.size == 0)
            binding.progressIndicator.visibility = View.VISIBLE
        // for getting the current location update after every 2 seconds with high accuracy


        val locationRequest = LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 2000
        }

        /*  val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
              .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) */
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)

                    if (!LocationValueListener.locationOn) {
                        fusedLocationClient.removeLocationUpdates(this)
                    }

                    for (location in locationResult.locations) {

                        if (location != null) {
                            if (lista_de_visite.size == 0) {
                                LocationValueListener.myLocation = location
                                getVisites()

                            } else {
                                LocationValueListener.myLocation = location
                                main_viste_adapter.setVisite(lista_de_visite)
                            }
                        } else
                            askForPermissions()
                    }

                }
            },

            Looper.myLooper()!!
        )
    }

    fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showPermissionDeniedDialog()
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            }
            return false
        } else {
            if (CheckGpsStatus()) {
                setUpLocationListener()

            } else {
                showPermissionDeniedGPS()
            }
        }
        return true
    }

    fun isPermissionsAllowed(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Autorisation refusée")
            .setMessage("L’autorisation est refusée, veuillez autoriser les autorisations à partir des paramètres de l’application.")
            .setPositiveButton("Paramètres de l’application",
                DialogInterface.OnClickListener { _, _ ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel", null)
            .show()
    }

    //Check GPS Status if Activated or not
    fun CheckGpsStatus(): Boolean {
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return GpsStatus
    }

    //Show Denied GPS
    private fun showPermissionDeniedGPS() {
        AlertDialog.Builder(requireContext())
            .setTitle("Autorisation GPS")
            .setMessage("Veuillez autoriser le GPS à partir des paramètres de l’application.")
            .setPositiveButton("Paramètres de l’application",
                DialogInterface.OnClickListener { _, _ ->
                    // send to app settings if permission is denied permanently

                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)

                })
            .setCancelable(false)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationValueListener.locationOn = false
        fm = null
    }

    override fun onClosedCheckDialog() {
        getVisites()
    }


}

object StaticMapClicked {
    var mapIsRunning = false
}

object LocationValueListener {
    lateinit var myLocation: Location
    var locationOn = true
}
