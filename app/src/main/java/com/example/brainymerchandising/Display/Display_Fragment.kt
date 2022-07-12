package com.example.brainymerchandising.Display

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.Display.Adapter.Adapter_base_Display
import com.example.brainymerchandising.Display.Adapter.Image_Adapter
import com.example.brainymerchandising.Display.Model.*
import com.example.brainymerchandising.Display.ViewModel.Display_ViewModel
import com.example.brainymerchandising.Utils.resources.Resource
import com.example.brainymerchandising.databinding.FragmentDisplayBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_display_.*
import kotlinx.android.synthetic.main.fragment_display_.view.*
import kotlinx.android.synthetic.main.item_display.*
import kotlinx.android.synthetic.main.item_display.view.*
import kotlinx.android.synthetic.main.item_display_type_with_without.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class Display_Fragment : Fragment(), Adapter_base_Display.Base_DisplayListener,Image_Adapter.ImageItemListener {
    private val viewModel: Display_ViewModel by viewModels()
    private var liste_objet_display = ArrayList<DisplayType>()
    private var liste_Category_display = ArrayList<Display_category>()
    private var liste_Brand_display = ArrayList<Display_Brand>()
    private var liste_objet_displaySection = ArrayList<DisplaySections>()
    private lateinit var liste_objet_displayCategory : Resource<Display_Category_Get_Response>
    private lateinit var liste_objet_displayBrand : Resource<List_Brand_Get_Response>
    private var liste_Image: ArrayList<Image>? = ArrayList<Image>()
    lateinit var sharedPref: SharedPreferences
    private var fm: FragmentManager? = null
    private lateinit var binding: FragmentDisplayBinding
    private lateinit var responseData_Display_Type: Resource<ListGetResponse_DisplayType>
    private lateinit var adapter_Display_base: Adapter_base_Display
    private lateinit  var selected_Element  : Any
    private lateinit  var   adapter_image:  Image_Adapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDisplayBinding.inflate(inflater, container, false)
        fm = childFragmentManager

        sharedPref = requireContext().getSharedPreferences(
            com.example.brainymerchandising.R.string.app_name.toString(),
            Context.MODE_PRIVATE)
        //Init childFragmentManager
        fm = childFragmentManager

        Get_Display_Type()
        Get_Display_Category()
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.subjectText.setOnItemClickListener { parent, view, position, id ->
            selected_Element = parent.getItemAtPosition(position)
            // Log.d("selected_Element",selected_Element.toString())



            binding.fab.setOnClickListener(View.OnClickListener {

                Log.d("liste_objet_display",
                    (this.activity as PrimeActivity).tab_CustomFieldValues!!.toString())

            })


            Get_Display_section() }}

    private fun Get_Display_section(){

        loop@for(i in liste_objet_display){
           // Log.d("liste_objet_display",liste_objet_display.toString())

            if( i.name.equals(selected_Element.toString())){

                if(!i.withBrand){
                    (this.activity as PrimeActivity).tab_CustomFieldValues!!.clear()
                    binding.layoutContainer.item_brand_category.visibility = View.VISIBLE

                }
                for (j in i.displaySections){
                    liste_objet_displaySection.add(j)
                    //Log.d("liste_objet_displaySection",liste_objet_displaySection.toString())
                }
                break@loop

            }}
        setupRecycleView()
        liste_objet_displaySection.clear()
    }


    private fun Get_Display_Type(){
        lifecycleScope.launch(Dispatchers.Main) {
            if(isAdded){
                responseData_Display_Type = viewModel.getDisplayType()
                if(responseData_Display_Type.responseCode == 200)
                    liste_objet_display = responseData_Display_Type.data!!.data as ArrayList<DisplayType>
                val arrayAdapter = ArrayAdapter(requireContext(),
                    com.example.brainymerchandising.R.layout.dropdown_choix_display,
                    liste_objet_display.map { it -> it.name})
                //Log.d("lista",liste_objet_display.toString())

                binding.subjectText.setAdapter(arrayAdapter)



            }}}

    private fun Get_Display_Category(){
        lifecycleScope.launch(Dispatchers.Main) {

            if(isAdded){

                liste_objet_displayCategory = viewModel.getDisplayCategory()
                liste_objet_displayBrand = viewModel.getDisplayBrand()

                if((liste_objet_displayCategory.responseCode == 200)&&
                        (liste_objet_displayBrand.responseCode == 200)){
                    liste_Category_display = liste_objet_displayCategory.data!!.data as ArrayList<Display_category>
                liste_Brand_display = liste_objet_displayBrand.data!!.data as ArrayList<Display_Brand>

                val arrayAdapter = ArrayAdapter(requireContext(),
                    com.example.brainymerchandising.R.layout.dropdown_choix_display,
                    liste_Category_display.map { it -> it.name})
                Log.d("lista",liste_objet_display.toString())

                val arrayAdapter2 = ArrayAdapter(requireContext(),
                    com.example.brainymerchandising.R.layout.dropdown_choix_display,
                    liste_Brand_display.map { it -> it.name})
                Log.d("lista",liste_objet_display.toString())

                binding.layoutContainer.subjectCategory.setAdapter(arrayAdapter)
                binding.layoutContainer.subjectBrand.setAdapter(arrayAdapter2)




            }}





        }
    }
    private fun setupRecycleView() {

        adapter_Display_base = Adapter_base_Display(this, requireActivity(),liste_objet_displaySection)
        binding.listeDisplayRecycle.isMotionEventSplittingEnabled = false
        binding.listeDisplayRecycle.layoutManager = LinearLayoutManager(requireContext())
        binding.listeDisplayRecycle.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false)

        binding.listeDisplayRecycle.adapter = adapter_Display_base
        //Log.d("ena",liste_objet_display.toString())

        adapter_Display_base.setItems(liste_objet_displaySection)


    }


    override fun onClickeddisplay(position: Int) {
        TODO("Not yet implemented")

    }

    override fun onClickedImage(position: Int) {
        TODO("Not yet implemented")
    }


}