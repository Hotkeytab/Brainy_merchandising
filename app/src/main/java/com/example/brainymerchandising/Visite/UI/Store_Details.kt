package com.example.brainymerchandising.Visite.UI

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.brainymerchandising.R
import com.example.brainymerchandising.databinding.FragmentMainVisiteBinding
import com.example.brainymerchandising.databinding.FragmentStoreBinding
import com.example.brainymerchandising.databinding.FragmentStoreDetailsBinding
import kotlinx.android.synthetic.main.fragment_login.*


class Store_Details : Fragment() {

    private lateinit var binding: FragmentStoreDetailsBinding
    private lateinit var navController: NavController
    private var fm: FragmentManager? = null
    lateinit var sharedPref: SharedPreferences



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoreDetailsBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)

        binding.backToMainMenu.setOnClickListener {
            navController.navigate(R.id.action_store_Details_to_mainVisiteFragment)

        }
        binding.MenuDisplay.setOnClickListener {
           // navController.navigate(R.id.action_store_Details_to_suivieFragment)

        }
        binding.MenuStock.setOnClickListener {
            navController.navigate(R.id.action_store_Details_to_productFragment)}
    }}
