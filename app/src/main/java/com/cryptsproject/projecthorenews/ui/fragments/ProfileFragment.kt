package com.cryptsproject.projecthorenews.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cryptsproject.projecthorenews.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_Signout.setOnClickListener {
            signoutUser()
        }
    }

    private fun signoutUser(){
        FirebaseAuth.getInstance().signOut()
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment2)
    }
}
