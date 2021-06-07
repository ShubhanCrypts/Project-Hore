package com.cryptsproject.projecthorenews.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cryptsproject.projecthorenews.R
import com.cryptsproject.projecthorenews.adapter.NewsAdapter
import com.cryptsproject.projecthorenews.models.Article
import com.cryptsproject.projecthorenews.models.ArticleDummy
import com.cryptsproject.projecthorenews.models.Source
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_saved.*

class SavedFragment : Fragment(R.layout.fragment_saved) {

    lateinit var newsAdapter: NewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedFragment_to_detailsFragment,
                bundle
            )
        }

//        val adapter = NewsAdapter()
        val list = mutableListOf<Article>()
//        rvSavedFragment.layoutManager = LinearLayoutManager(requireContext())
//        rvSavedFragment.adapter = adapter

        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("newsss").get().addOnSuccessListener { querySnapshot ->
            querySnapshot.forEach { document ->
                val source = document.getString("source.id")?.let {
                    Source(
                        it,
                        document.getString("source.name") ?: ""
                        )
                }

                list.add(
                    Article(
                        document.getString("author") ?: "",
                        document.getString("content") ?: "",
                        document.getString("description") ?: "",
                        document.getString("publishedAt") ?: "",
                        source,
                        document.getString("title") ?: "",
                        document.getString("url") ?: "",
                        document.getString("urlToImage") ?: ""

                    )
                )
            }
            newsAdapter.differ.submitList(list)
        }


    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        rvSavedFragment.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}