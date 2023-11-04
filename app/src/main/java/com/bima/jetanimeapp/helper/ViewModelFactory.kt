package com.bima.jetanimeapp.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bima.jetanimeapp.data.AnimeRepository

class ViewModelFactory(private val repository: AnimeRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
    }
}