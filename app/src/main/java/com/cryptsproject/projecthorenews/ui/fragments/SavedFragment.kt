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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlin.math.*
import java.text.SimpleDateFormat

class SavedFragment : Fragment(R.layout.fragment_saved) {

    lateinit var newsAdapter: NewsAdapter

    private fun actualityLevel(publishedAt : String) : Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val publishedTime = dateFormat.parse(publishedAt).time
        val currentTime = System.currentTimeMillis()
        val timeDiff = currentTime - publishedTime
        val finalScore = max(0.0,floor(10*(10-log(ceil(timeDiff/86400000.0),2.0))))
        return finalScore.toInt()
    }

    private fun readingIntensity(user_id : String) : Int {
        val firestore = FirebaseFirestore.getInstance()
        val intensity = firestore.collection(user_id).document("intensity").get().data
        // var intensity = mutableListOf(0)
        val currentTime = System.currentTimeMillis().toInt()
        val millisInADay = 86400000
        val intensityToday = intensity.count{it > currentTime-millisInADay}
        if (intensityToday >= 20)
            return 0
        intensity.removeIf{it < currentTime-7*millisInADay}
        intensity.add(currentTime)
        firestore.collection(user_id).document("intensity").set(intensity)
        var finalScore = 0
        val historyPoints = arrayOf(5,4,3,3,2,2,1)
        val intensityPoints = arrayOf(10,10,10,9,9,9,8,8,8,7,7,6,6,5,5,4,4,3,2,1)
        for (i in 0..6)
            if (intensity.count{it/millisInADay == currentTime/millisInADay - i} > 0)
                finalScore += historyPoints[i]*intensityPoints[intensityToday]
        return finalScore
    }

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

        val userid = FirebaseAuth.getInstance().currentUser?.uid

        firestore.collection("$userid").get().addOnSuccessListener { querySnapshot ->
            querySnapshot.forEach { document ->
                var point = firestore.collection("$userid").document("point").get().data ?: 0
                var finalScore = actualityLevel(document.getString("publishedAt")
                // finalScore *= readingIntensity("$userid")
                point += finalScore
                firestore.collection("$userid").document("point").set(point)
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
