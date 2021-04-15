package com.cryptsproject.projecthorenews.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.cryptsproject.projecthorenews.R
import com.cryptsproject.projecthorenews.ui.MainActivity
import com.cryptsproject.projecthorenews.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    lateinit var viewModel: MainViewModel
    val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val newsArticle = args.article
        news_web_view.apply {
            webViewClient = WebViewClient()
            loadUrl(newsArticle.url)
        }
    }





}