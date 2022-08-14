package com.example.brainymerchandising.Product.UI

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Utils.FixedHeader.MatrixTableAdapter
import com.example.brainymerchandising.databinding.FragmentHistoriqueBinding
import com.example.brainymerchandising.databinding.FragmentStoreBinding


class Historique_Fragment : Fragment() {
    private lateinit var binding: FragmentHistoriqueBinding
    private var fm: FragmentManager? = null

    lateinit var sharedPref: SharedPreferences



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoriqueBinding.inflate(inflater, container, false)
        fm = childFragmentManager

        sharedPref = requireContext().getSharedPreferences(
            com.example.brainymerchandising.R.string.app_name.toString(),
            Context.MODE_PRIVATE)
        inflateTable()
        // Inflate the layout for this fragment
        return binding.root
    }




    fun inflateTable() {
        val matrixTableAdapter = MatrixTableAdapter(
            context, arrayOf(
                arrayOf(
                    "Label",
                    "Internal Code",
                    "Product COde",
                    "17 juin 2022",
                    "19 juin 2022",
                    "5 juillet 2022"
                ), arrayOf(
                    "ffe",
                    "sed",
                    "do",
                    "efiusmod",
                    "tempor",
                    "incididunt"
                ), arrayOf(
                    "fe",
                    "fwe",
                    "few",
                    "enim",
                    "laborum",
                    "reprehenderit"
                ), arrayOf(
                    "fwe",
                    "fw",
                    "fwe",
                    "reprehenderit",
                    "laborum",
                    "consequat"
                ), arrayOf(
                    "few",
                    "consequat",
                    "fwe",
                    "w",
                    "eiusmod",
                    "enim"
                ), arrayOf(
                    "amet",
                    "nulla",
                    "Excepteur",
                    "voluptate",
                    "occaecat",
                    "et"
                ), arrayOf(
                    "consectetur",
                    "occaecat",
                    "fugiat",
                    "dolore",
                    "consequat",
                    "eiusmod"
                ), arrayOf(
                    "adipisicing",
                    "fugiat",
                    "Excepteur",
                    "occaecat",
                    "fugiat",
                    "laborum"
                ), arrayOf(
                    "elit",
                    "voluptate",
                    "reprehenderit",
                    "Excepteur",
                    "fugiat",
                    "nulla"
                )
            )
        )
        binding.tableHisto.setAdapter(matrixTableAdapter)
    }
    }