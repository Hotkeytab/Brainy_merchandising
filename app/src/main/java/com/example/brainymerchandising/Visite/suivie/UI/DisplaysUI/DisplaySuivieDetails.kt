package com.example.brainymerchandising.Visite.suivie.UI.DisplaysUI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainymerchandising.Visite.suivie.UI.DisplaysUI.adapters.AdapterDisplaySuivieDetails
import com.example.brainymerchandising.Visite.suivie.model.VisiteSuivie
import com.example.brainymerchandising.databinding.FragmentDisplaysSuivieBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [DisplaySuivieDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplaySuivieDetails : Fragment(),AdapterDisplaySuivieDetails.Suivie_DisplayDetailsListener {
    private lateinit var binding: FragmentDisplaysSuivieBinding
    private lateinit var adapter_Suivie_Details: AdapterDisplaySuivieDetails
    private lateinit var visiteResponseSuivie: VisiteSuivie
    private var DisplayID : Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDisplaysSuivieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        visiteResponseSuivie = getArguments()!!.getSerializable("productList") as VisiteSuivie
        DisplayID = getArguments()!!.getInt("DispID")

        Log.d("maherDisplayID",DisplayID.toString())
        Log.d("maherDisplayID",visiteResponseSuivie.toString())
        setupRecycleView()


    }

    private fun setupRecycleView() {


        adapter_Suivie_Details = AdapterDisplaySuivieDetails(this, requireActivity(),
            visiteResponseSuivie,DisplayID
        )
        binding.listeDisplayRecycleSuive.isMotionEventSplittingEnabled = false
        binding.listeDisplayRecycleSuive.layoutManager = LinearLayoutManager(requireContext())
        binding.listeDisplayRecycleSuive.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false)

        binding.listeDisplayRecycleSuive.adapter = adapter_Suivie_Details
        adapter_Suivie_Details.setItems(visiteResponseSuivie.displays[DisplayID].displayType!!.displaySections)
        //Log.d("ena",liste_objet_display.toString())

       // adapter_Suivie_Details.setItems(visiteResponseSuivie.displays as ArrayList<Displays>)


    }

    override fun onClickeddisplay(position: Int) {
val a =1    }


}