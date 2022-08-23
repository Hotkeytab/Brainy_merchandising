package com.example.brainymerchandising.Product.UI

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.brainymerchandising.Product.Model.GetRefProduct_Response
import com.example.brainymerchandising.Product.Model.Product
import com.example.brainymerchandising.Product.Model.ProductRef
import com.example.brainymerchandising.Product.ViewModel.Product_ViewModel
import com.example.brainymerchandising.Utils.FixedHeader.Adapters.MyAdapter
import com.example.brainymerchandising.Utils.resources.Resource
import com.example.brainymerchandising.Visite.Store.Model.Store
import com.example.brainymerchandising.databinding.FragmentHistoriqueBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_add_visite.*
import kotlinx.android.synthetic.main.fragment_historique.*

@AndroidEntryPoint
class Historique_Fragment : Fragment() {
    private lateinit var adapter: MyAdapter
    private lateinit var binding: FragmentHistoriqueBinding
    private var fm: FragmentManager? = null

    lateinit var sharedPref: SharedPreferences
    private lateinit var _Ref_Product_Response1 : Resource<GetRefProduct_Response>
    private var liste_product_ref1 = ArrayList<ProductRef>()
    private var liste_product_= ArrayList<Product>()
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


        for (i in liste_product_ref1)
            liste_product_.add(i.product)



        adapter = MyAdapter(context!!)
        adapter.setItems(liste_product_)
        binding.tableHisto.setAdapter(adapter)

        Log.d("contra",liste_product_ref1.toString())

        return binding.root
    }

    private fun filterResearch(name: String, editTextName: String): Boolean {
        var patternRegex = ""
        var patternRegex2 = ""
        var count = 0
        var count2 = 0

        val editTextName2 = editTextName.replace('?', '+')
        val chunks = editTextName2.toUpperCase().split("\\s+".toRegex())


        for (i in chunks) {
            if (count == 0) {
                patternRegex = "^($i.+)"
            } else {
                patternRegex += "\\s+($i.)"
            }
            count++
        }

        for (j in chunks) {
            if (count2 == 0) {
                patternRegex2 = "^($j+)"
            } else {
                patternRegex2 += "\\s+($j)"
            }
            count2++
        }

        val regexFilter = Regex(patternRegex)
        val regexFilter2 = Regex(patternRegex2)

        return regexFilter.containsMatchIn(name.toUpperCase()) || regexFilter2.containsMatchIn(name.toUpperCase())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Get_list_Ref_product1()


        search_text_histo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                if (search_text_histo.text.toString().trim().isEmpty()) {


                    adapter = MyAdapter(context!!)
                    adapter.setItems(liste_product_)
                    binding.tableHisto.setAdapter(adapter)
                } else {
                    val newArrayList = liste_product_.filter { list ->
                        filterResearch(
                            list.label,
                            search_text_histo.text.toString().trim()
                        )
                    }
                    adapter = MyAdapter(context!!)
                    adapter.setItems(newArrayList as ArrayList<Product>)
                    binding.tableHisto.setAdapter(adapter)
                }


            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })








}










    }


