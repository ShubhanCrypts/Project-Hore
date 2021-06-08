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

    private fun actualityScore(publishedAt : String) : Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val publishedTime = dateFormat.parse(publishedAt).time
        val currentTime = System.currentTimeMillis()
        val timeDiff = currentTime - publishedTime
        val finalScore = max(0.0,floor(10*(10-log(ceil(timeDiff/86400000.0),2.0))))
        return finalScore.toInt()
    }

    private fun intensityScore(readingIntensity : MutableList<Int>, currentTime : Int) : Int {
        val millisInADay = 86400000
        val intensityToday = readingIntensity.count{it > currentTime-millisInADay}
        if (intensityToday > 20)
            return 0
        val historyPoints = arrayOf(5,4,3,3,2,2,1)
        val intensityPoints = arrayOf(10,10,10,9,9,9,8,8,8,7,7,6,6,5,5,4,4,3,2,1)
        var finalScore = 0
        for (i in 0..6)
            if (readingIntensity.count{it/millisInADay == currentTime/millisInADay - i} > 0)
                finalScore += historyPoints[i]*intensityPoints[intensityToday-1]
        return finalScore
    }

    private fun repetitionScore(finishedReading : Int, untilFinish : Int) : Int {
        val repetitionPoints = arrayOf(
            arrayOf(3,1,0),
            arrayOf(5,3,1)
        )
        val finalScore = repetitionPoints[untilFinish][finishedReading]
        return finalScore
    }

    private fun evaluateRank(finalScore : Int) : String {
        val difficulty = 10000
        var rank = 0
        var bound = difficulty
        while (rank < 14 && finalScore >= bound) {
            rank++
            bound = 2 * bound + difficulty
        }
        val award = arrayOf("Bronze","Silver","Gold","Platinum","Diamond")
        val finalRank = "${award[rank/3]} ${3-rank%3}"
        return finalRank
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
                val articleid = document.getString("url")
                val currentTime = System.currentTimeMillis().toInt()
                val millisInADay = 86400000
                var readingIntensity = mutableListOf(currentTime)
                var finishedReading = 0
                
                // read all reading history
                val userRating = firestore.collection("rating").document("$userid")
                val readingHistory = userRating.collection("history")
                readingHistory.get()
                    .addOnSuccessListener{ docs ->
                        for (doc in docs) {
                            val readingTime = doc.getString("timestamp").toInt()
                            if (readingTime > currentTime - 7 * millisInADay)
                                readingIntensity.add(readingTime)
                            if (doc.getString("articleid").equals(articleid))
                                finishedReading = max(finishedReading, doc.getString("finished").toInt()+1)
                        }
                    }
                
                // evaluate reading score
                var finalScore = actualityScore(document.getString("publishedAt"))
                finalScore *= intensityScore(readingIntensity, currentTime)
                finalScore *= repetitionScore(finishedReading, untilFinish)
                userRating.get()
                    .addOnSuccessListener{ doc ->
                        finalScore += doc.getString("rating").toInt()
                    }
                
                // save new record
                val newRating = hashMapOf(
                    "rating" to finalScore.toString(),
                    "rank" to evaluateRank(finalScore)
                )
                userRating.set(newRating)
                val newHistory = hashMapOf(
                    "timestamp" to currentTime.toString(),
                    "articleid" to articleid,
                    "finished" to untilFinish.toString()
                )
                readingHistory.document(currentTime.toString()).set(newHistory)

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
