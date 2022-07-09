package com.example.brainymerchandising.Visite.UI.Dialog.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Utils.resources.Resource
import com.example.brainymerchandising.Visite.UI.MainVisiteAdapter
import com.example.brainymerchandising.Visite.visite.Model.DeleteVisiteResponse
import com.example.brainymerchandising.Visite.visite.Model.Visite
import com.example.brainymerchandising.Visite.visite.ViewModel.VisiteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.will_check.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class Delete_Visite (
    visiteId: Int,
    positionVisite: Int,
    taskAdapter2: MainVisiteAdapter,
    items2: ArrayList<Visite>,
    //items3: ArrayList<Visite>
): DialogFragment() {

    val visiteId = visiteId
    val positionVisite = positionVisite
    private lateinit var response: Resource<DeleteVisiteResponse>
    private val viewModel: VisiteViewModel by viewModels()
    private val main_visiteAdapter = taskAdapter2
    private val items = items2
   // private val items4 = items3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.will_check, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog!!.window!!.setWindowAnimations(R.style.AnimationsForMapDialogSwipe)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Accept to Delete Visite
        accept.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            accept.isEnabled = false
            cancel_button.isEnabled = false
            dialog!!.setCancelable(false)

            lifecycleScope.launch(Dispatchers.Main) {
                response = viewModel.deleteVisite(visiteId)

                if (response.responseCode == 202) {
                    progress_bar.visibility = View.GONE
                    items.removeAt(positionVisite)
                  //  items4.removeAt(positionVisite)
                    main_visiteAdapter.notifyDataSetChanged()
                    dialog!!.dismiss()
                } else {
                    progress_bar.visibility = View.GONE
                    accept.isEnabled = true
                    cancel_button.isEnabled = true
                    dialog!!.setCancelable(true)

                }
            }


        }

        cancel_button.setOnClickListener {
            dialog!!.dismiss()
        }

    }


}

