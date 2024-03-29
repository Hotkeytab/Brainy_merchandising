package com.example.brainymerchandising.Display.UI.Dialog

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.BuildConfig
import com.example.brainymerchandising.Display.Adapter.Image_Adapter
import com.example.brainymerchandising.Display.Display_Fragment
import com.example.brainymerchandising.Display.Model.Image
import com.example.brainymerchandising.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_choix_image.*
import java.io.File


@AndroidEntryPoint
class Choix_Image(
    imagehaha2: ImageView,
    adapterImage2: Image_Adapter,
    listaImage2: ArrayList<Image>?,
    plus_image2: LinearLayout,
    recycle_view2: RecyclerView,
    displayFragment: Display_Fragment,
    SectionId : Int
) :
    DialogFragment() {

    private val imagehaha = imagehaha2
    private var adapterImage = adapterImage2
    private val listaImage = listaImage2
    private lateinit var imageBitmap: Image
    private val plus_image = plus_image2
    private val recycle_view = recycle_view2
    private val displayFragment = displayFragment
    private var uri: Uri? = null
    private var SectionId = SectionId


    //Our constants
    private val CAPTURE_PHOTO = 1
    private val CHOOSE_PHOTO = 2


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_choix_image, container, false)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        camera_pick.setOnClickListener { capturePhoto() }
        gallery_pick.setOnClickListener {
            //check permission at runtime
            val checkSelfPermission = ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                //Requests permissions to be granted to this application at runtime
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            } else {
                //Open Gallery to get Image
                openGallery()}
        }

    }


    private fun show(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    //Capture Photo Camera
    private fun capturePhoto() {
        val capturedImage = File(requireActivity().externalCacheDir, "My_Captured_Photo.jpg")
        if (capturedImage.exists()) {
            capturedImage.delete()
        }
        //Create new file from captured Image
        capturedImage.createNewFile()
        //get new Uri from captured image that turned into file
        uri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                requireContext(), BuildConfig.APPLICATION_ID + ".provider",
                capturedImage
            )
        } else {
            Uri.fromFile(capturedImage)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, CAPTURE_PHOTO)
    }

    //Open Gallery instead of Capture Camera
    private fun openGallery() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, CHOOSE_PHOTO)
    }

    //Render Select Image if Path exists
    private fun renderImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            imagehaha.setImageBitmap(bitmap)

            imageBitmap = Image(0, bitmap,"",SectionId,imagePath)
            recycle_view.visibility = View.VISIBLE
            (displayFragment.activity as PrimeActivity).tab_Image!!.add(imageBitmap)
            //listaImage!!.add(imageBitmap)
            for(i in 0..(displayFragment.activity as PrimeActivity).tab_Image!!.size-1 ){
                (displayFragment.activity as PrimeActivity).tab_Image!!.get(i).id=i
            }



            adapterImage.setItems((displayFragment.activity as PrimeActivity).tab_Image!!)
            (displayFragment.activity as PrimeActivity).adapterImage = adapterImage

            Afficher_Image_DIalog(
                1,
                (displayFragment.activity as PrimeActivity).tab_Image!!,
                plus_image,
                displayFragment,
                1,
                SectionId,
                adapterImage
            ).show(getActivity()!!.supportFragmentManager, "afficherimage")
        } else {
            show("ImagePath is null")
        }
    }

    //Get Seleected Image Path
    @SuppressLint("Range")
    private fun getImagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = requireActivity().contentResolver.query(uri!!, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }


    //This function is for api 19 Kitkat version
    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(requireContext(), uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority) {
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion
                )
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse(
                        "content://downloads/public_downloads"
                    ), java.lang.Long.valueOf(docId)
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            imagePath = uri.path
        }
        renderImage(imagePath)
    }

    //Handle After Permission
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantedResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        when (requestCode) {
            1 ->
                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    openGallery()
                } else {
                    show("Unfortunately You are Denied Permission to Perform this Operataion.")
                }
        }
    }

    //Handle AFter choose Image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(
                        requireActivity().contentResolver.openInputStream(uri!!)
                    )
                    imagehaha.setImageBitmap(bitmap)
                    imageBitmap = Image(0, bitmap,"",SectionId,uri!!.path.toString())
                    recycle_view.visibility = View.VISIBLE

                    (displayFragment.activity as PrimeActivity).tab_Image!!.add(imageBitmap)
                    //listaImage!!.add(imageBitmap)
                    adapterImage.setItems((displayFragment.activity as PrimeActivity).tab_Image!!)
                    //listaImage!!.add(imageBitmap)

                   // adapterImage.setItems(listaImage)
                }
            CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                }
        }

    }
}



