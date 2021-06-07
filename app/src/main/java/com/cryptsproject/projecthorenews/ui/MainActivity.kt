package com.cryptsproject.projecthorenews.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cryptsproject.projecthorenews.R
import com.cryptsproject.projecthorenews.repository.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelProviderFactory = MainViewModelProviderFactory(repository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)

        bottomNavigation.setupWithNavController(activityNavFragment.findNavController())
        activityNavFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loginFragment2 || destination.id == R.id.signupFragment2){
                bottomNavigation.visibility = View.GONE
            }else{
                bottomNavigation.visibility = View.VISIBLE
            }
        }
<<<<<<< HEAD

//        val navController = this.findNavController(R.id.activity_main_navgraph)
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            if(destination.id == R.id.loginFragment2 || destination.id == R.id.signupFragment2){
//                bottomNavigation.visibility = View.GONE
//            }else{
//                bottomNavigation.visibility = View.VISIBLE
//            }
//        }


=======
>>>>>>> origin/login-register
    }
}