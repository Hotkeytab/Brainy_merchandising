package com.example.brainymerchandising.Product.UI.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.brainymerchandising.Product.Model.ProductRef
import com.example.brainymerchandising.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_set_amount.*

@AndroidEntryPoint
class SetAmount_product (
        position: Int,
        liste_product: ArrayList<ProductRef>)
        : DialogFragment() {

        private var position = position
        private val liste_product = liste_product
        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                return inflater.inflate(R.layout.dialog_set_amount, container, false)
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

                if (!val_quantite.text.isEmpty()){


                }


        }


        }
