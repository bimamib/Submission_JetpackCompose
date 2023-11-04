package com.bima.jetanimeapp.di

import com.bima.jetanimeapp.data.AnimeRepository

object Injection {
    fun provideRepository(): AnimeRepository {
        return AnimeRepository.getInstance()
    }
}