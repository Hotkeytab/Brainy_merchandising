package com.example.brainymerchandising.Visite.UI

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Visite.suivie.model.VisiteSuivie
import com.example.brainymerchandising.Visite.visite.Model.Visite
import com.example.brainymerchandising.databinding.FragmentMainVisiteBinding
import com.example.brainymerchandising.databinding.FragmentStoreBinding
import com.example.brainymerchandising.databinding.FragmentStoreDetailsBinding
import kotlinx.android.synthetic.main.fragment_login.*


class Store_Details : Fragment() {

    private lateinit var binding: FragmentStoreDetailsBinding
    private lateinit var navController: NavController
    private var fm: FragmentManager? = null
    lateinit var sharedPref: SharedPreferences

    private lateinit var visiteResponse: Visite
    private lateinit var visiteResponseSuivie: VisiteSuivie
    private  var Flag: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoreDetailsBinding.inflate(inflater, container, false)

        return binding.root}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Flag = getArguments()!!.getInt("Flag")

        if (Flag == 2){
            visiteResponseSuivie = getArguments()!!.getSerializable("productList") as VisiteSuivie

            binding.MenuLogOut.visibility = View.GONE
            if (visiteResponseSuivie.displays.isEmpty()){
                binding.MenuDisplay.visibility = View.GONE
            }
            if (visiteResponseSuivie.orders.isEmpty()){
                binding.MenuOrder.visibility = View.GONE
            }
            if (visiteResponseSuivie.stocks.isEmpty()){
                binding.MenuStock.visibility = View.GONE
            }
        }

        if (Flag == 1){
            binding.backToMainMenu.setOnClickListener {
                navController.navigate(R.id.action_store_Details_to_mainVisiteFragment)

            }
            binding.MenuDisplay.setOnClickListener {
                navController.navigate(R.id.action_store_Details_to_display_Fragment2)
            }
            binding.MenuStock.setOnClickListener {
                navController.navigate(R.id.action_store_Details_to_productFragment)}
            }

         // Log.d("jassa",visiteResponse.toString())

        navController = NavHostFragment.findNavController(this)

        binding.backToMainMenu.setOnClickListener {
            navController.navigate(R.id.action_store_Details_to_mainVisiteFragment)

        }
        binding.MenuDisplay.setOnClickListener {
           navController.navigate(R.id.action_store_Details_to_display_Fragment2)
        }
        binding.MenuStock.setOnClickListener {
            navController.navigate(R.id.action_store_Details_to_productFragment)}
    }}
