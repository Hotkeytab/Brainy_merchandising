package com.example.brainymerchandising.Visite.suivie.UI

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Utils.Calendar.Abstract.DateWatcher
import com.example.brainymerchandising.Utils.Calendar.Abstract.OnDateClickListener
import com.example.brainymerchandising.Utils.Calendar.Model.CalendarDay
import com.example.brainymerchandising.Utils.Calendar.UI.VerticalWeekCalendar
import com.example.brainymerchandising.Utils.Calendar.controller.VerticalWeekAdapter
import com.example.brainymerchandising.Utils.resources.Resource
import com.example.brainymerchandising.Visite.UI.MainVisiteAdapter
import com.example.brainymerchandising.Visite.suivie.Adapter.Suivie_Visite_adapter
import com.example.brainymerchandising.Visite.visite.Model.ListVisiteGet
import com.example.brainymerchandising.Visite.visite.Model.Visite
import com.example.brainymerchandising.Visite.visite.ViewModel.VisiteViewModel
import com.example.brainymerchandising.databinding.FragmentGlobalsuivieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.calendar_view.view.*
import kotlinx.android.synthetic.main.fragment_main_visite.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class SuivieFragment : Fragment() , Suivie_Visite_adapter.VisiteItemListener_suivie{
    lateinit var selected: GregorianCalendar
    lateinit var calendarView: VerticalWeekCalendar
    private lateinit var binding: FragmentGlobalsuivieBinding
    private val viewModel: VisiteViewModel by viewModels()
    private lateinit var responseData: Resource<ListVisiteGet>
    lateinit var sharedPref: SharedPreferences
    private lateinit var navController: NavController
    private var userId = 0
    private var lista_de_visite = ArrayList<Visite>()
    private lateinit var main_viste_adapter: Suivie_Visite_adapter







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        sharedPref = requireContext().getSharedPreferences(
            R.string.app_name.toString(),
            Context.MODE_PRIVATE
        )
        userId = sharedPref.getInt("id", 0)

        binding = FragmentGlobalsuivieBinding.inflate(inflater, container, false)


        return binding.root }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        selected = GregorianCalendar(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH] )


        calendarView = VerticalWeekCalendar.Builder()
            .setView(R.id.verticalCalendar)
            .init(view)
        calendarView.setOnDateClickListener(object : OnDateClickListener {
            override fun onCalenderDayClicked(year: Int, month: Int, day: Int) {
                val selectedDay = GregorianCalendar(year, month, day)
                if (selected.compareTo(selectedDay) != 0) {
                    selected = selectedDay
                }
            }
        })


        calendarView.setDateWatcher(object : DateWatcher {
            override fun getStateForDate(year: Int, month: Int, day: Int, view: VerticalWeekAdapter.DayViewHolder?): Int {
                return if (selected!!.compareTo(
                        GregorianCalendar(
                            year,
                            month,
                            day
                        )
                    ) == 0
                ) CalendarDay.SELECTED else CalendarDay.DEFAULT
            }
        })
        if (isAdded && activity != null) {
            navController = NavHostFragment.findNavController(this)

            getVisites_Suivie() }





}

    @DelicateCoroutinesApi
    private fun getVisites_Suivie() {

        lifecycleScope.launch(Dispatchers.Main) {
            if (!isDetached) {
                if (isAdded) {
                    responseData =
                        viewModel.getVisites(userId.toString(), "2022-09-13", "2022-09-13")
                    if (responseData.responseCode == 200) {
                        lista_de_visite = responseData.data!!.data as ArrayList<Visite>

                        if (lista_de_visite.size == 0)
                        // binding.novisit.visibility = View.VISIBLE
                        else
                        //  binding.novisit.visibility = View.GONE

                            if (isAdded && activity != null)
                                setupRecycleViewSuivieDetail()
                        binding.root.progress_indicator.visibility = View.GONE
                    }
                }
            }

        }
    }

    private fun setupRecycleViewSuivieDetail() {

        main_viste_adapter = Suivie_Visite_adapter(
            this, requireActivity(),
            activity as PrimeActivity, lista_de_visite, navController
        )

        binding.root.Suivie_planning_Recy.isMotionEventSplittingEnabled = false
        binding.root.Suivie_planning_Recy.layoutManager = LinearLayoutManager(requireContext())
        binding.root.Suivie_planning_Recy.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.root.Suivie_planning_Recy.adapter = main_viste_adapter
        main_viste_adapter.setVisite(lista_de_visite)
        main_viste_adapter.notifyDataSetChanged()


    }

    override fun onClickedVisite(
        taskId: Int,
        distance: String,
        visite: Visite,
        theDistance: Float
    ) {
        TODO("Not yet implemented")
    }

    override fun onClosedCheckDialog() {
        TODO("Not yet implemented")
    }

}