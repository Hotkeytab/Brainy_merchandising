package com.example.brainymerchandising.Activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.brainymerchandising.Display.Adapter.Image_Adapter
import com.example.brainymerchandising.Display.Model.Image
import com.example.brainymerchandising.Display.Model.Post.CustomFieldValue
import com.example.brainymerchandising.Product.Model.ProductRef
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Visite.UI.MainVisiteFragment
import com.example.brainymerchandising.baseNav.Base_nav
import com.example.brainymerchandising.Visite.suivie.UI.SuivieFragment

import dagger.hilt.android.AndroidEntryPoint
import com.example.brainymerchandising.databinding.ActivityPrimeBinding
import kotlinx.android.synthetic.main.activity_home.*


@AndroidEntryPoint
class PrimeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fm: FragmentManager

    // private val viewModel: DrawerActivityViewModel by viewModels()
    lateinit var sharedPref: SharedPreferences

    //lateinit var responseDataUser: Resource<UserResponse>
    //   private lateinit var sessionManager: SessionManager
    //private lateinit var user: User
    private var picture: String? = null

    var tab_Image: ArrayList<Image>? = ArrayList<Image>()
    var tab_CustomFieldValues: ArrayList<CustomFieldValue>? = ArrayList<CustomFieldValue>()
    var ProductrefHistorique: ArrayList<ProductRef>? = ArrayList<ProductRef>()
    var tab_CustomFieldValues1: HashMap<Int, CustomFieldValue> = HashMap<Int, CustomFieldValue>()
    lateinit var adapterImage: Image_Adapter
    var tabImageALlSection: HashMap<Int, ArrayList<Image>> = HashMap<Int, ArrayList<Image>>()


    //Current fragment
    var nowFragment = 0

    //Last fragment
    var lastFragment = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_prime)

        //Init session manager + fragment manager
        fm = this.supportFragmentManager
        //Init Navigation View
        val mNavigationView = findViewById<NavigationView>(R.id.nav_view)
        mNavigationView.setNavigationItemSelectedListener(this)

        //Get Profile Service
        supportFragmentManager.beginTransaction().replace(
            R.id.nav_acceuil_fragment,
            Base_nav()
        ).commit()


        //Bottom Nav Bar Listener






    }


    //Check if selectedFragment is the currentFragment
    private fun checkForFragment(): Boolean {
        if (nowFragment == lastFragment)
            return false
        else {
            lastFragment = nowFragment
            return true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }


    // Save User to SharedPref

}

