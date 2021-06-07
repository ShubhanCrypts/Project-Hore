package com.cryptsproject.projecthorenews.ui.fragments

import android.content.Intent
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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    lateinit var viewModel: MainViewModel
    val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        detailActionBar.inflateMenu(R.menu.menu_detail)

        val firestore = FirebaseFirestore.getInstance()

        viewModel = (activity as MainActivity).viewModel
        val newsArticle = args.article
        news_web_view.apply {
            webViewClient = WebViewClient()
            loadUrl(newsArticle.url)
        }

        val link = newsArticle.url


        val article = hashMapOf(
            "author" to newsArticle.author.toString(),
            "content" to newsArticle.content.toString(),
            "description" to newsArticle.description.toString(),
            "publishedAt" to newsArticle.publishedAt.toString(),
            "source.name" to newsArticle.source?.name.toString(),
            "source.id" to newsArticle.source?.id.toString(),
            "title" to newsArticle.title.toString(),
            "url" to newsArticle.url,
            "urlToImage" to newsArticle.urlToImage.toString()

        )

//        fab.setOnClickListener {
//            firestore.collection("newsss").document().set(article)
//
//        }
//
//        fab_share.setOnClickListener {
//            val sendIntent: Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, link)
//                type = "text/plain"
//            }
//
//            val shareIntent = Intent.createChooser(sendIntent, null)
//            startActivity(shareIntent)
//        }

        detailActionBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_save -> {
                    firestore.collection("newsss").document().set(article)
                    true
                }
                R.id.action_share -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, link)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                    true
                }
                else -> false
            }
        }


    }









}