package com.example.brainymerchandising.Product.UI

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.brainymerchandising.Product.Model.GetRefProduct_Response
import com.example.brainymerchandising.Product.Model.ProductRef
import com.example.brainymerchandising.Product.ViewModel.Product_ViewModel
import com.example.brainymerchandising.Utils.FixedHeader.Adapters.MyAdapter
import com.example.brainymerchandising.Utils.resources.Resource
import com.example.brainymerchandising.databinding.FragmentHistoriqueBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Historique_Fragment : Fragment() {
    private lateinit var adapter: MyAdapter
    private lateinit var binding: FragmentHistoriqueBinding
    private var fm: FragmentManager? = null

    lateinit var sharedPref: SharedPreferences
    private lateinit var _Ref_Product_Response1 : Resource<GetRefProduct_Response>
    private var liste_product_ref1 = ArrayList<ProductRef>()
    private  val viewModel1: Product_ViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentHistoriqueBinding.inflate(inflater, container, false)
        fm = childFragmentManager

        sharedPref = requireContext().getSharedPreferences(
            com.example.brainymerchandising.R.string.app_name.toString(),
            Context.MODE_PRIVATE)
        //inflateTable()
        // Inflate the layout for this fragment

        // Log.d("contra",activity!!.applicationContext.toString())

        liste_product_ref1 = (arguments!!.getSerializable("productList") as ArrayList<ProductRef>?)!!

        adapter = MyAdapter(context!!)
        adapter.setItems(liste_product_ref1)
        binding.tableHisto.setAdapter(adapter)

        Log.d("contra",liste_product_ref1.toString())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Get_list_Ref_product1()











}










    }


