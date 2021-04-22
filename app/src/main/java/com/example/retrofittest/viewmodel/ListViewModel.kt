package com.example.retrofittest.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.retrofittest.model.remote.datamodel.News
import com.example.retrofittest.repository.NewsRepository

class ListViewModel(
    repository: NewsRepository
) : ViewModel() {

    private var _query= MutableLiveData<String>()
    var data:LiveData<PagingData<News>> =Transformations.switchMap(_query){
        repository.getNews(it).cachedIn(viewModelScope)
    }
    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    init {
        _query.value="food"
    }

    fun fetchNewData(text:String) {
        _query.value=text
    }

    fun messageReceived() {
        _message.value = null
    }
}

class ListViewModelFactory(
    private val repository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(repository) as T
        throw Exception("")
    }
}