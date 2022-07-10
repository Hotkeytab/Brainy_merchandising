package com.example.brainymerchandising.Product.UI.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.brainymerchandising.Product.Model.POST.productPost
import com.example.brainymerchandising.Product.Model.Product
import com.example.brainymerchandising.Product.Model.ProductRef
import com.example.brainymerchandising.Product.ViewModel.Product_ViewModel
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Utils.resources.ConstModele.SuccessResponse
import com.example.brainymerchandising.Utils.resources.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_add_visite.*
import kotlinx.android.synthetic.main.dialog_set_amount.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetAmount_product (
        position: Int,
        product: ProductRef,
        )

        : DialogFragment() {
        private val viewModel: Product_ViewModel by viewModels()
        private lateinit var responseAdd: Resource<SuccessResponse>
        private var position = position
        private val product = product
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


                ok_amount.setOnClickListener {
                        progress_indicatorproduct.visibility = View.VISIBLE

                        if (!val_quantite.text.isEmpty()) {
                                val Postproduct = productPost(
                                        0, product.productId, product.storeId,
                                        val_quantite.text.toString().toInt(), false
                                )
                                GlobalScope.launch(Dispatchers.Main) {
                                        responseAdd = viewModel.updateStock(Postproduct)


                                if (responseAdd.responseCode == 201) {
                                        dialog!!.setCancelable(true)
                                        cancel.isEnabled = true
                                        dialog!!.dismiss()

                                } else {
                                        dialog!!.setCancelable(true)
                                        cancel.isEnabled = true
                                        progress_indicatorproduct.visibility = View.GONE
                                }


                        }

                        }


        }


        }}
