package com.example.retrofittest.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofittest.model.remote.RetrofitService
import com.example.retrofittest.model.remote.datamodel.News

private const val FIRST_PAGE_NUMBER=1

class NewsDataSource(private val query:String): PagingSource<Int, News>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val position=params.key?: FIRST_PAGE_NUMBER
            val response =RetrofitService.apiService.getAll(query,position,params.loadSize)
            val news=response.news
            LoadResult.Page(
                data = news,
                prevKey = if (position== FIRST_PAGE_NUMBER)null else position-1,
                nextKey = if (news.isEmpty())null else position+1
            )
        }catch (ex:Exception){
            LoadResult.Error(ex)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}