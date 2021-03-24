package com.cryptsproject.projecthorenews.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
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
    }
}