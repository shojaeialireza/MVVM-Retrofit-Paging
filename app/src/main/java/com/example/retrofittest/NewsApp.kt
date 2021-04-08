package com.example.retrofittest

import android.app.Application
import com.example.retrofittest.repository.NewsRepository

class NewsApp:Application() {
    val repository:NewsRepository by lazy { NewsRepository.getInstance() }
}