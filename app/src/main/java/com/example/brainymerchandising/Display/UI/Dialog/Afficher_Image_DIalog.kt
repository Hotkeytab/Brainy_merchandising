package com.example.brainymerchandising.Display.UI.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.Display.Adapter.Image_Adapter
import com.example.brainymerchandising.Display.Display_Fragment
import com.example.brainymerchandising.Display.Model.Image
import com.example.brainymerchandising.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_afficher_image.*

@AndroidEntryPoint
class Afficher_Image_DIalog(
    position2: Int,
    listaImage2: ArrayList<Image>?,
    plus_image2: LinearLayout,
    display_fragment: Display_Fragment,
    Flag: Int,
    SectionId: Int,
    adapterImage: Image_Adapter

) :
    DialogFragment() {


    private var position = position2
    private val listaImage = listaImage2
    private val plus_image = plus_image2
    private val Flag = Flag
    private val SectionId = SectionId


    private val display_fragment = display_fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_afficher_image, container, false)
    }

    override fun onStart() {
        super.onStart()

        //Prepare Dialog Size
        val width = (resources.displayMetrics.widthPixels)
        val height = (resources.displayMetrics.heightPixels)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window?.setLayout(width, height)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Show Image from Lista Array Image


        if (Flag == 0) {
            ok_amount.visibility = View.GONE
            var listeUnique: ArrayList<Image>? = ArrayList<Image>()
            for (i in listaImage!!) {
                if (i.SectionId == SectionId) {
                    listeUnique!!.add(i)}
            }

            afficher_image.setImageBitmap(listeUnique!![position].url)
            val_quantite.text = Editable.Factory.getInstance().newEditable(listeUnique!![position].text)

            //Navigate to Previous Image
            left_arrow.setOnClickListener {
                if (position != 0 && listeUnique.size != 1) {
                    position--
                    afficher_image.setImageBitmap(listeUnique[position].url)
                    val_quantite.text =
                        Editable.Factory.getInstance().newEditable(listeUnique!![position].text)

                }
            }

            //Navigate to Next Image
            right_arrow.setOnClickListener {
                if (listeUnique.size - 1 != position && listeUnique.size != 1) {
                    position++
                    afficher_image.setImageBitmap(listeUnique[position].url)
                    val_quantite.text =
                        Editable.Factory.getInstance().newEditable(listeUnique!![position].text)

                }

            }


            //Delete Current Image
            delete.setOnClickListener {
                if (listeUnique.size == 1) {
                    listaImage.removeAt(listeUnique.get(0).id)
                    for (i in 0..listaImage.size - 1) {
                        listaImage!!.get(i).id = i
                    }
                    (display_fragment.activity as PrimeActivity).tab_Image = listaImage
                    (display_fragment.activity as PrimeActivity).adapterImage.setItems(listaImage)
                    listeUnique.clear()
                    for (i in listaImage!!) {
                        if (i.SectionId == SectionId) {
                            listeUnique!!.add(i)
                        }
                    }
                    dismiss()
                } else if (listeUnique.size > 1) {
                    if (position == 0) {
                        listaImage.removeAt(listeUnique.get(0).id)
                        listeUnique.removeAt(0)
                        afficher_image.setImageBitmap(listeUnique[position].url)
                        for (i in 0..listaImage.size - 1) {
                            listaImage!!.get(i).id = i
                        }
                        (display_fragment.activity as PrimeActivity).tab_Image = listaImage
                        (display_fragment.activity as PrimeActivity).adapterImage.setItems(
                            listaImage
                        )
                        listeUnique.clear()
                        for (i in listaImage!!) {
                            if (i.SectionId == SectionId) {
                                listeUnique!!.add(i)
                            }
                        }
                    } else if (position == listeUnique.size - 1) {
                        listaImage.removeAt(listeUnique.get(listeUnique.size - 1).id)
                        listeUnique.removeAt(listeUnique.size - 1)
                        position--
                        for (i in 0..listaImage.size - 1) {
                            listaImage!!.get(i).id = i
                        }
                        (display_fragment.activity as PrimeActivity).tab_Image = listaImage
                        afficher_image.setImageBitmap(listeUnique[position].url)
                        (display_fragment.activity as PrimeActivity).adapterImage.setItems(
                            listaImage
                        )
                        listeUnique.clear()
                        for (i in listaImage!!) {
                            if (i.SectionId == SectionId) {
                                listeUnique!!.add(i)
                            }
                        }
                    } else {
                        listaImage.removeAt(listeUnique.get(position).id)
                        listeUnique.removeAt(position)
                        afficher_image.setImageBitmap(listeUnique[position].url)
                        for (i in 0..listaImage.size - 1) {
                            listaImage!!.get(i).id = i
                        }
                        (display_fragment.activity as PrimeActivity).tab_Image = listaImage
                        (display_fragment.activity as PrimeActivity).adapterImage.setItems(
                            listaImage
                        )
                        listeUnique.clear()
                        for (i in listaImage!!) {
                            if (i.SectionId == SectionId) {
                                listeUnique!!.add(i)
                            }
                        }
                    }
                } else
                    dismiss()
            }


        }
        if (Flag == 1) {
            return_from_dialog.visibility = View.GONE
            right_arrow.visibility = View.GONE
            delete.visibility = View.GONE
            left_arrow.visibility = View.GONE
            var size = (display_fragment.activity as PrimeActivity).tab_Image!!.size - 1
            afficher_image.setImageBitmap(listaImage!![size].url)

            ok_amount.setOnClickListener {
                if (!val_quantite.text.isEmpty()) {
                    (display_fragment.activity as PrimeActivity).tab_Image!!.get(size).text =
                        val_quantite.text.toString()
                } else {
                    (display_fragment.activity as PrimeActivity).tab_Image!!.get(size).text = ""
                }
                dismiss()
            }


        }


        //Close Dialog
        return_from_dialog.setOnClickListener {
            dismiss()
        }


    }


}



