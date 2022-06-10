package com.example.brainymerchandising.Login.UI


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.Login.Model.SignInResponse
import com.example.brainymerchandising.Login.Model.UserInfo_LoginPost
import com.example.brainymerchandising.Login.Model.UserResponse
import com.example.brainymerchandising.Login.ViewModel.SignInFragmentViewModel
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Utils.Token.SessionManager
import com.example.brainymerchandising.Utils.extensions.trimStringEditText
import com.example.brainymerchandising.Utils.resources.Network.InternetCheck
import com.example.brainymerchandising.Utils.resources.Network.InternetCheckDialog
import com.example.brainymerchandising.Utils.resources.Resource
import com.example.brainymerchandising.databinding.FragmentLoginBinding


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() , DialogInterface.OnDismissListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var sessionManager: SessionManager
    private lateinit var fm: FragmentManager
    private lateinit var dialog: InternetCheckDialog
    private lateinit var binding: FragmentLoginBinding
    private lateinit var responseDataSignIn: Resource<SignInResponse>
    private val viewModel: SignInFragmentViewModel by viewModels()
    private var responseDataUser: Resource<UserResponse>? = null
    lateinit var sharedPref: SharedPreferences







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)


        if(isAdded){

            sessionManager = SessionManager(requireContext())
            fm = requireActivity().supportFragmentManager
            dialog = InternetCheckDialog()


            binding.signinButton.setOnClickListener {
                //Disable signin button until we get a response from webservice
                binding.signinButton.isEnabled = false
                checkInternet()
            }


        }

        return binding.root


    }

    @DelicateCoroutinesApi
    private fun signIn() {



        //If Username is empty
        if (binding.username.editText!!.trimStringEditText().isEmpty()) {
            //Prepare Error
            clearError()
            binding.username.error = "Username is Empty"
            binding.signinButton.isEnabled = true
            //If password is empty
        } else if (binding.password.editText!!.trimStringEditText().isEmpty()) {
            //Prepare Error
            clearError()
            binding.password.error = "Password is Empty"
            binding.signinButton.isEnabled = true
        } else {

            //Activate Progress Indicator
            binding.progressIndicator.visibility = View.VISIBLE


            //Call CheckInternet function
            InternetCheck { internet ->
                //If Internet is Good
                if (internet) {
                    //Create SignIn Object
                    val sinInObject = UserInfo_LoginPost(
                        binding.username.editText!!.trimStringEditText(),
                        binding.password.editText!!.trimStringEditText()
                    )


                    //If fragment is Added
                    if (isAdded) {
                        //Launch Couroutine
                        GlobalScope.launch(Dispatchers.Main) {
                            //Response from SignIn Webservice
                            responseDataSignIn = viewModel.login(sinInObject)

                            //If response is good
                            if (responseDataSignIn.responseCode == 200) {
                                //Save Login Infos to shared pref
                                //rememberMe()
                                sessionManager.saveToken(responseDataSignIn.data!!.token)
                                Log.d("meher", sessionManager.fetchRefreshToken().toString())

                                //Fetch DataUser
                                responseDataUser =
                                    viewModel.getUser(binding.username.editText!!.trimStringEditText())
                              //  Log.d("meher",responseDataUser.toString())


                                //Save User ID to shared pref
                                sharedPref =
                                    requireActivity().getSharedPreferences(
                                        R.string.app_name.toString(),
                                        Context.MODE_PRIVATE
                                    )!!
                                with(sharedPref.edit()) {
                                    this?.putInt("id", responseDataUser!!.data!!.data.id)
                                }?.commit()

                                //Start Intent after everything is good
                                val intent = Intent(activity, PrimeActivity::class.java)
                                activity?.startActivity(intent)
                                activity?.finish()


                                //Password is wrong
                            } else if (responseDataUser != null) {

                                if (responseDataUser!!.responseCode == 401) {
                                    binding.signinButton.isEnabled = true
                                    binding.progressIndicator.visibility = View.INVISIBLE
                                    clearError()
                                    binding.password.error =
                                        "Mot de passe ou nom d'utilisateur Erroné "
                                    //Connection Problems
                                } else {
                                    binding.signinButton.isEnabled = true
                                    binding.progressIndicator.visibility = View.INVISIBLE
                                    clearError()
                                    binding.password.error = "Erreur Connexion "
                                }
                                //Connection Problems
                            } else {
                                binding.signinButton.isEnabled = true
                                binding.progressIndicator.visibility = View.INVISIBLE
                                clearError()
                                binding.password.error = "Erreur Connexion"
                            }
                        }
                    }


                }
            }
        }
    }
    private fun clearError() {
        binding.username.error = null
        binding.password.error = null
    }
    //OnDismissDialog (check internet dialog)
    //SignIn Again if dialog is closed
    override fun onDismiss(p0: DialogInterface?) {
        signIn()
    }

    //Check Internet
    private fun checkInternet() {
        InternetCheck { internet ->
            //If Internet Good
            if (internet)
                signIn()

            //If There is no Internet
            else {

                //Progress bar invisible
                binding.progressIndicator.visibility = View.INVISIBLE

                //Show Internet check Dialog
                dialog.show(
                    fm,
                    "Internet check"
                )
                fm.executePendingTransactions()

                //Dialog cancel listener
                dialog.dialog!!.setOnCancelListener {
                    checkInternet()
                }

            }
        }
    }




}
