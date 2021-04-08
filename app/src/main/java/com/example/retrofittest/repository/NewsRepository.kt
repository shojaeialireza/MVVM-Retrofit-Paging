package com.example.retrofittest.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.retrofittest.model.NewsDataSource

class NewsRepository private constructor(){

    companion object{
        private var INSTANCE:NewsRepository?=null
        fun getInstance():NewsRepository{
            if (INSTANCE==null)
                INSTANCE= NewsRepository()
            return INSTANCE!!
        }
    }

    fun getNews(query:String)=
        Pager(
            PagingConfig(pageSize = 3, enablePlaceholders = false),
            pagingSourceFactory = { NewsDataSource(query) }
        ).liveData
}