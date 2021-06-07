package com.cryptsproject.projecthorenews.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cryptsproject.projecthorenews.R
//import com.cryptsproject.projecthorenews.databinding.FragmentLoginBinding
import com.cryptsproject.projecthorenews.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {


//    private var _binding : FragmentLoginBinding? = null
//
//    private val binding
//        get() =_binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        return binding.root
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        bottomNavigation.visibility = View.GONE
//    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bottomNavigation.visibility = View.GONE

        val user = Firebase.auth.currentUser
        if(user != null){
            findNavController().navigate(R.id.action_loginFragment2_to_homeFragment)
        }
        btnLogin.setOnClickListener {
            loginUser()
        }

        tvSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_signupFragment2)
        }

        tvSignup.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragment2ToSignupFragment2()
            findNavController().navigate(action)
        }



    }

    private fun loginUser(){
        val email : String = et_email_login.text.toString()
        val password :String = et_password_login.text.toString()
        if(email.isNullOrEmpty() or password.isNullOrEmpty()){

        }else{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        findNavController().navigate(R.id.action_loginFragment2_to_homeFragment)
                    }
                }
        }



    }

}