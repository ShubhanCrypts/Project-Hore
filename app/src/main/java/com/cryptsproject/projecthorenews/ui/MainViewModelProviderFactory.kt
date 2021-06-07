package com.cryptsproject.projecthorenews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cryptsproject.projecthorenews.repository.Repository

class MainViewModelProviderFactory(
    val newsRepository : Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(newsRepository) as T
    }
}