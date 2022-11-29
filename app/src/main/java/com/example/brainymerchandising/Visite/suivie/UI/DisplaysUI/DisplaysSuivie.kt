package com.example.brainymerchandising.Visite.suivie.UI.DisplaysUI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainymerchandising.Visite.suivie.UI.DisplaysUI.adapters.AdapterSuivieDisplay
import com.example.brainymerchandising.Visite.suivie.model.VisiteSuivie
import com.example.brainymerchandising.Visite.suivie.model.display.Displays
import com.example.brainymerchandising.databinding.FragmentDisplaysSuivieBinding

// TODO: Rename parameter arguments, choose names that match

class DisplaysSuivie : Fragment() , AdapterSuivieDisplay.Suivie_DisplayListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter_Suivie_base: AdapterSuivieDisplay
    private lateinit var visiteResponseSuivie: VisiteSuivie
    private lateinit var binding: FragmentDisplaysSuivieBinding
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        visiteResponseSuivie = getArguments()!!.getSerializable("productList") as VisiteSuivie

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplaysSuivieBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        setupRecycleView()}


    private fun setupRecycleView() {
        adapter_Suivie_base = AdapterSuivieDisplay(this, requireActivity(),
            visiteResponseSuivie , navController
        )
        binding.listeDisplayRecycleSuive.isMotionEventSplittingEnabled = false
        binding.listeDisplayRecycleSuive.layoutManager = LinearLayoutManager(requireContext())
        binding.listeDisplayRecycleSuive.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false)

        binding.listeDisplayRecycleSuive.adapter = adapter_Suivie_base
        //Log.d("ena",liste_objet_display.toString())
        adapter_Suivie_base.setItems(visiteResponseSuivie.displays as ArrayList<Displays>) }



    override fun onClickeddisplay(position: Int) {
        val a = 0 } }