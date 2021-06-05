package com.cryptsproject.projecthorenews.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.cryptsproject.projecthorenews.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment(R.layout.fragment_signup) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignup.setOnClickListener {
            signupUser()
        }
    }

    private fun signupUser(){
        val email: String = et_email.text.toString()
        val password: String = et_password.text.toString()
        if(email.isNullOrEmpty() or password.isNullOrEmpty()){

        }else{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        findNavController().navigate(R.id.action_signupFragment2_to_homeFragment)
                    }
                }
        }

    }
}