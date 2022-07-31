package com.example.brainymerchandising.Display

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.Display.Adapter.Adapter_base_Display
import com.example.brainymerchandising.Display.Adapter.Image_Adapter
import com.example.brainymerchandising.Display.Model.*
import com.example.brainymerchandising.Display.Model.Post.Data_image_post
import com.example.brainymerchandising.Display.Model.Post.Display
import com.example.brainymerchandising.Display.Model.Post.Items_Text_input
import com.example.brainymerchandising.Display.ViewModel.Display_ViewModel
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Utils.ProgressRequestBody
import com.example.brainymerchandising.Utils.extensions.getFileName
import com.example.brainymerchandising.Utils.resources.ConstModele.SuccessResponse
import com.example.brainymerchandising.Utils.resources.Resource
import com.example.brainymerchandising.databinding.FragmentDisplayBinding
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_display_.view.*
import kotlinx.android.synthetic.main.item_display_type_with_without.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


@AndroidEntryPoint
class Display_Fragment : Fragment(), Adapter_base_Display.Base_DisplayListener,
    Image_Adapter.ImageItemListener,   ProgressRequestBody.UploadCallbacks  {
    private val viewModel: Display_ViewModel by viewModels()
    private var liste_objet_display = ArrayList<DisplayType>()
    private var liste_Category_display = ArrayList<Display_category>()
    private var liste_Brand_display = ArrayList<Display_Brand>()
    private var liste_objet_displaySection = ArrayList<DisplaySections>()
    private lateinit var liste_objet_displayCategory: Resource<Display_Category_Get_Response>
    private lateinit var liste_objet_displayBrand: Resource<List_Brand_Get_Response>
    private var liste_Image: ArrayList<Image>? = ArrayList<Image>()
    lateinit var sharedPref: SharedPreferences
    private var fm: FragmentManager? = null
    private lateinit var binding: FragmentDisplayBinding
    private lateinit var responseData_Display_Type: Resource<ListGetResponse_DisplayType>
    private lateinit var adapter_Display_base: Adapter_base_Display
    private lateinit var selected_Element: Any
    private lateinit var adapter_image: Image_Adapter
    private lateinit var customFieldObject: DisplayCustomFields
    private lateinit var TextPostAll_EditText: Items_Text_input
    private lateinit var data_image_post: Data_image_post
    private lateinit var display: Display
    private  var displayId: Int = 0
    private var ImageALl_Array = ArrayList<Data_image_post>()
    lateinit var responseDisplay: Resource<SuccessResponse>
    var recentPercent = 0
    private var percent = 0
    private var filesNumber = 0






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDisplayBinding.inflate(inflater, container, false)
        fm = childFragmentManager

        sharedPref = requireContext().getSharedPreferences(
            com.example.brainymerchandising.R.string.app_name.toString(),
            Context.MODE_PRIVATE
        )
        //Init childFragmentManager
        fm = childFragmentManager

        Get_Display_Type()
        Get_Display_Category()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.subjectText.setOnItemClickListener { parent, view, position, id ->
            selected_Element = parent.getItemAtPosition(position)
            // Log.d("selected_Element",selected_Element.toString())
            var customValues: ArrayList<Items_Text_input>? = ArrayList<Items_Text_input>()

            val listMultipartBody = ArrayList<MultipartBody.Part?>()


            binding.fab.setOnClickListener(View.OnClickListener {


                  display = Display(sharedPref.getInt("storeId",0),sharedPref.getInt("userId",0)
                      ,displayId,null,null,sharedPref.getInt("visiteId",0))

               Log.d("display", display.toString())

                val DisplayFormData = jacksonObjectMapper().writeValueAsString(display)
                val displayJson = RequestBody.create(
                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                    DisplayFormData
                )

                for (i in (this.activity as PrimeActivity).tab_CustomFieldValues1!!) {

                    customFieldObject = DisplayCustomFields(
                        i.value.DisplayCustomFieldId, i.value.name,
                        i.value.type, null, null, i.value.displaySectionId
                    )

                    TextPostAll_EditText = Items_Text_input(
                        customFieldObject,
                        i.value.value,
                        i.value.DisplayCustomFieldId
                    )

                    customValues!!.add(TextPostAll_EditText)
                }

                val customValuesFormData = jacksonObjectMapper().writeValueAsString(customValues)
                val customValuesJson = RequestBody.create(
                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                    customValuesFormData
                )

               Log.d("liste_objet_display", (this.activity as PrimeActivity).tab_CustomFieldValues1!!.toString())

               Log.d("customValues", customValues!!.toString())
               Log.d("customValues", (this.activity as PrimeActivity).tab_Image!!!!.toString())

                for ( i in (this.activity as PrimeActivity).tab_Image!!){
                    Log.d("customValues", i.path!!.toString())

                    convertToFile(i, listMultipartBody)

                    data_image_post = Data_image_post(i.text,i.SectionId)
                    ImageALl_Array.add(data_image_post)
                }

                val dataFormData = jacksonObjectMapper().writeValueAsString(ImageALl_Array)
                val dataJson = RequestBody.create(
                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                    dataFormData
                )


               // Log.d("Imageupload", ImageALl_Array.toString())
                Log.d("displayJson", DisplayFormData!!.toString())
                Log.d("customValuesJson", customValuesFormData!!.toString())
                Log.d("dataJson", dataFormData!!.toString())
                Log.d("dataJson", listMultipartBody!!.toString())
                filesNumber = listMultipartBody.size
                binding.progressUpload.max = filesNumber * 2

                GlobalScope.launch(Dispatchers.Main) {
                    responseDisplay = viewModel.postDisplay(
                        listMultipartBody,
                        displayJson,
                        customValuesJson,
                        dataJson
                    ) as Resource<SuccessResponse>

                    Log.d("responsecode", responseDisplay.toString())

                    if (responseDisplay.responseCode == 201) {
                    val snack = Snackbar.make(
                        requireView(),
                        "Questionnaire Envoyé avec succès",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(R.color.purpleLogin)
                    val view: View = snack.view
                    val params = view.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.BOTTOM
                    view.layoutParams = params

                    snack.show()
                } else {
                    val snack =
                        Snackbar.make(requireView(), "Une Erreur s'est produite", Snackbar.LENGTH_LONG)

                    val view: View = snack.view
                    val params = view.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.BOTTOM
                    view.layoutParams = params
                    snack.show()

                }



                }
            })


            Get_Display_section()
        }
    }

    //Convert ALl Bmp Images to files
    private fun convertToFile(
        image: Image,
        listMultipartBody: ArrayList<MultipartBody.Part?>
    ): MultipartBody.Part? {

        val selectedImageUri = getImageUri(requireContext(), image.url)

        if (selectedImageUri != null) {
            val parcelFileDescriptor =
                requireActivity().contentResolver.openFileDescriptor(selectedImageUri, "r", null)
                    ?: return null

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)

            val file = File(
                requireActivity().cacheDir,
                requireActivity().contentResolver.getFileName(selectedImageUri)
            )

            val body = ProgressRequestBody(file, "image", this)


            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            val mbp = MultipartBody.Part.createFormData(
                "files", file.absolutePath,
                body
            )

            listMultipartBody.add(mbp)

            requireActivity().contentResolver.delete(selectedImageUri, null, null)

        }

        return null


    }


    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun Get_Display_section() {

        loop@ for (i in liste_objet_display) {
            // Log.d("liste_objet_display",liste_objet_display.toString())

            if (i.name.equals(selected_Element.toString())) {
                displayId = i.id

                if (!i.withBrand) {
                    (this.activity as PrimeActivity).tab_CustomFieldValues!!.clear()
                    binding.layoutContainer.item_brand_category.visibility = View.VISIBLE

                }
                for (j in i.displaySections) {
                    liste_objet_displaySection.add(j)
                    //Log.d("liste_objet_displaySection",liste_objet_displaySection.toString())
                }
                break@loop

            }
        }
        setupRecycleView()
        liste_objet_displaySection.clear()
    }


    private fun Get_Display_Type() {
        lifecycleScope.launch(Dispatchers.Main) {
            if (isAdded) {
                responseData_Display_Type = viewModel.getDisplayType()
                if (responseData_Display_Type.responseCode == 200)
                    liste_objet_display =
                        responseData_Display_Type.data!!.data as ArrayList<DisplayType>
                val arrayAdapter = ArrayAdapter(requireContext(),
                    com.example.brainymerchandising.R.layout.dropdown_choix_display,
                    liste_objet_display.map { it -> it.name })
                //Log.d("lista",liste_objet_display.toString())

                binding.subjectText.setAdapter(arrayAdapter)


            }
        }
    }

    private fun Get_Display_Category() {
        lifecycleScope.launch(Dispatchers.Main) {

            if (isAdded) {

                liste_objet_displayCategory = viewModel.getDisplayCategory()
                liste_objet_displayBrand = viewModel.getDisplayBrand()

                if ((liste_objet_displayCategory.responseCode == 200) &&
                    (liste_objet_displayBrand.responseCode == 200)
                ) {
                    liste_Category_display =
                        liste_objet_displayCategory.data!!.data as ArrayList<Display_category>
                    liste_Brand_display =
                        liste_objet_displayBrand.data!!.data as ArrayList<Display_Brand>

                    val arrayAdapter = ArrayAdapter(requireContext(),
                        com.example.brainymerchandising.R.layout.dropdown_choix_display,
                        liste_Category_display.map { it -> it.name })
                    Log.d("lista", liste_objet_display.toString())

                    val arrayAdapter2 = ArrayAdapter(requireContext(),
                        com.example.brainymerchandising.R.layout.dropdown_choix_display,
                        liste_Brand_display.map { it -> it.name })
                    Log.d("lista", liste_objet_display.toString())

                    binding.layoutContainer.subjectCategory.setAdapter(arrayAdapter)
                    binding.layoutContainer.subjectBrand.setAdapter(arrayAdapter2)


                }
            }


        }
    }

    private fun setupRecycleView() {

        adapter_Display_base =
            Adapter_base_Display(this, requireActivity(), liste_objet_displaySection)
        binding.listeDisplayRecycle.isMotionEventSplittingEnabled = false
        binding.listeDisplayRecycle.layoutManager = LinearLayoutManager(requireContext())
        binding.listeDisplayRecycle.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.listeDisplayRecycle.adapter = adapter_Display_base
        //Log.d("ena",liste_objet_display.toString())

        adapter_Display_base.setItems(liste_objet_displaySection)


    }


    override fun onClickeddisplay(position: Int) {
val a =0
    }

    override fun onClickedImage(position: Int) {
        val b =0
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onProgressUpdate(percentage: Int) {


        if (percentage < recentPercent) {
            percent++
            if ((((percent.toFloat() / (filesNumber * 2)) * 100).toInt()) <= 100) {
                binding.textPercentage.text =
                    (((percent.toFloat() / (filesNumber * 2)) * 100).toInt()).toString() + "%"
            }
            binding.progressUpload.setProgress(percent, true)
            recentPercent = 0
        } else {
            recentPercent = percentage
        }


    }
    override fun onError() {
        TODO("Not yet implemented")
    }

    override fun onFinish(finished: Boolean) {
        TODO("Not yet implemented")
    }


}