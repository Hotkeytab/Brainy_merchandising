package com.example.brainymerchandising.Visite.suivie.UI

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CalendarView
import androidx.core.os.trace
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
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
import com.example.brainymerchandising.Visite.suivie.Adapter.Suivie_Visite_adapter
import com.example.brainymerchandising.Visite.suivie.model.VisiteSuivie
import com.example.brainymerchandising.Visite.suivie.model.VisiteSuivieGet
import com.example.brainymerchandising.Visite.visite.ViewModel.VisiteViewModel
import com.example.brainymerchandising.databinding.FragmentGlobalsuivieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.calendar_view.*
import kotlinx.android.synthetic.main.calendar_view.view.*
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
    private lateinit var responseData: Resource<VisiteSuivieGet>
    lateinit var sharedPref: SharedPreferences
    private lateinit var navController: NavController
    private var userId = 0
    private var lista_de_visite = ArrayList<VisiteSuivie>()
    private lateinit var main_viste_adapter: Suivie_Visite_adapter


    private val intToMonth =
        arrayOf("Today","JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

    private val intToYear =
        arrayOf("2015","2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027")



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
        navController = NavHostFragment.findNavController(this)

        val calendar = Calendar.getInstance()
        selected = GregorianCalendar(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH] )






        VerticalWeekCalendar.today= 1

       //VerticalWeekCalendar.m =10
        calendarView = VerticalWeekCalendar.Builder()
            .setView(R.id.verticalCalendar)
            .init(view)
        val v = view

        val arrayAdapter = ArrayAdapter(requireContext(),
            R.layout.dropdown_choix_display,
            intToMonth)
        binding.CalendarParent.subjectText.setAdapter(arrayAdapter)


        val arrayAdapte1r = ArrayAdapter(requireContext(),
            R.layout.dropdown_choix_display,
            intToYear)
        binding.CalendarParent.subjectyear.setAdapter(arrayAdapte1r)




        //Log.d("lista",liste_objet_display.toString())
        binding.CalendarParent.subjectText.setOnItemClickListener { parent, view, position, id ->

            if (    parent.getItemAtPosition(position).equals("Today")){
                VerticalWeekCalendar.today= 1
            }else{
                VerticalWeekCalendar.today=0
                VerticalWeekCalendar.m= position-1

            Log.d("TodayBoo",  VerticalWeekCalendar.today.toString())}

            Log.d("ItemPosit",   parent.getItemAtPosition(position).toString())

         calendarView.setupRecyclerView()

        }

        binding.CalendarParent.subjectyear.setOnItemClickListener { parent, view, position, id ->

                 VerticalWeekCalendar.y= Integer.parseInt(parent.getItemAtPosition(position).toString())

            calendarView.setupRecyclerView()

        }



        calendarView.setOnDateClickListener(object : OnDateClickListener {
            override fun onCalenderDayClicked(year: Int, month: Int, day: Int) {
                val selectedDay = GregorianCalendar(year, month, day)
                if (selected.compareTo(selectedDay) != 0) {
                    val moutht = month+1
                    lista_de_visite.clear()


                    if (isAdded && activity != null) {

                        getVisites_Suivie(year.toString(),moutht.toString(),day.toString()) }
                    Log.d("year",year.toString())

                    Log.d("mounth",moutht.toString())

                    Log.d("day",day.toString())

                    selected = selectedDay
                }}})


        calendarView.setDateWatcher(object : DateWatcher {
            override fun getStateForDate(year: Int, month: Int, day: Int, view: VerticalWeekAdapter.DayViewHolder?): Int {
                return if (selected!!.compareTo(
                        GregorianCalendar(
                            year,
                            month,
                            day)) == 0

                ) CalendarDay.SELECTED else CalendarDay.DEFAULT}
        })






}

    @DelicateCoroutinesApi
    private fun getVisites_Suivie(year: String, month: String, day: String) {
        binding.root.progress_indicator.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.Main) {
            if (!isDetached) {

                if(isAdded){
                    responseData = viewModel.
                    getVisitesSuivie(userId.toString(),
                        year+"-"+month+"-"+day, year+"-"+month+"-"+day)
                    if (responseData.responseCode == 200) {
                       // Log.d("jassa",day.toString())
                        lista_de_visite = responseData.data!!.data as ArrayList<VisiteSuivie>
                       Log.d("jassa",lista_de_visite.toString())
                        if (lista_de_visite.size == 0)
                         //binding.novisit.visibility = View.VISIBLE
                        else
                        //  binding.novisit.visibility = View.GONE

                            if (isAdded && activity != null){
                                 setupRecycleViewSuivieDetail()
                        binding.root.progress_indicator.visibility = View.GONE}

                    }}}}
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
        visite: VisiteSuivie,
        theDistance: Float
    ) {
val a =1    }

    override fun onClosedCheckDialog() {
        TODO("Not yet implemented")
    }

}