package com.example.brainymerchandising.baseNav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.brainymerchandising.R
import com.example.brainymerchandising.databinding.FragmentBaseNavBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Base_nav : Fragment() {


    private lateinit var binding: FragmentBaseNavBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentBaseNavBinding.inflate(inflater,container, false)



        return binding.root
    }


}
