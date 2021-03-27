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

//        btnLogin.setOnClickListener {
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
//            activity?.finish()
//        }

        btnLogin.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragment2ToHomeFragment()
            findNavController().navigate(action)
        }

        tvSignup.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragment2ToSignupFragment2()
            findNavController().navigate(action)
        }


//        tvSignup.setOnClickListener {
//            val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
//            findNavController().navigate(action)
//        }



    }

}