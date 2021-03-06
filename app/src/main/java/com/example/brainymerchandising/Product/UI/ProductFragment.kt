package com.example.brainymerchandising.Product.UI

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainymerchandising.Product.Adapters.adapter_Product_base
import com.example.brainymerchandising.Product.Model.GetRefProduct_Response
import com.example.brainymerchandising.Product.Model.GetStock_Setting
import com.example.brainymerchandising.Product.Model.ProductRef
import com.example.brainymerchandising.Product.Model.StockSetting
import com.example.brainymerchandising.Product.ViewModel.Product_ViewModel
import com.example.brainymerchandising.Utils.resources.Resource
import com.example.brainymerchandising.databinding.FragmentStoreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment() , adapter_Product_base.Base_ProductListener{
    private lateinit var binding: FragmentStoreBinding
    lateinit var sharedPref: SharedPreferences
    private var fm: FragmentManager? = null
    private val viewModel: Product_ViewModel by viewModels()
    private lateinit var _Ref_Product_Response : Resource<GetRefProduct_Response>
    private lateinit var _Get_StockSetting_Response : Resource<GetStock_Setting>
    private lateinit var adapter_Product_base: adapter_Product_base
    private var liste_product_ref = ArrayList<ProductRef>()

    private var liste_SockSetting = ArrayList<StockSetting>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        sharedPref = requireContext().getSharedPreferences(
            com.example.brainymerchandising.R.string.app_name.toString(),
            Context.MODE_PRIVATE)

        fm = childFragmentManager
        GetStock_Setting()
        Get_list_Ref_product()


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
    }


    private fun Get_list_Ref_product() {
        lifecycleScope.launch(Dispatchers.Main) {

            if(isAdded){
                _Ref_Product_Response = viewModel.getRefProduct(2)

                if(_Ref_Product_Response.responseCode == 200){
                    liste_product_ref = _Ref_Product_Response.data!!.data as ArrayList<ProductRef>
                    Log.d("jiren",liste_product_ref.toString())

                }
                setupRecycleView()
            }}

    }

    private fun GetStock_Setting(){

        lifecycleScope.launch(Dispatchers.Main) {

            if(isAdded){
                _Get_StockSetting_Response = viewModel.getStockSetting()

                if(_Get_StockSetting_Response.responseCode == 200){
                    liste_SockSetting = _Get_StockSetting_Response.data!!.data as ArrayList<StockSetting>
                    Log.d("stockSetting",liste_SockSetting.toString())

                }
            }}





    }

    private fun setupRecycleView() {



        adapter_Product_base = adapter_Product_base(this, requireActivity(),liste_product_ref,binding.amount,requireActivity()
            ,liste_SockSetting,binding.sendStockOut,        binding.listeProductRecycle
        )

        binding.listeProductRecycle.isMotionEventSplittingEnabled = false
        binding.listeProductRecycle.layoutManager = LinearLayoutManager(requireContext())
        binding.listeProductRecycle.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false)

        binding.listeProductRecycle.adapter = adapter_Product_base

        adapter_Product_base.setItems(liste_product_ref)

    }

    override fun onClickedProduct(position: Int) {
        TODO("Not yet implemented")
    }


}