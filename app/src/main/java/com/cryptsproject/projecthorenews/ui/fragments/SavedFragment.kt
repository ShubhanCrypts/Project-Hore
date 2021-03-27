package com.cryptsproject.projecthorenews.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cryptsproject.projecthorenews.R
import com.cryptsproject.projecthorenews.adapter.DummyAdapter
import com.cryptsproject.projecthorenews.adapter.NewsAdapter
import com.cryptsproject.projecthorenews.models.ArticleDummy
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_saved.*

class SavedFragment : Fragment(R.layout.fragment_saved) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DummyAdapter()
        val list = mutableListOf<ArticleDummy>()
        rvSavedFragment.layoutManager = LinearLayoutManager(requireContext())
        rvSavedFragment.adapter = adapter

        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("news").get().addOnSuccessListener { querySnapshot ->
            querySnapshot.forEach { document ->
                list.add(
                    ArticleDummy(
                        document.id,
                        document.getString("title") ?: "",
                        document.getString("desc") ?: ""
                    )
                )
            }
            adapter.differ.submitList(list)
        }
    }


}
