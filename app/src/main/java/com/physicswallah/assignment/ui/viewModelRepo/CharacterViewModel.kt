package com.physicswallah.assignment.ui.viewModelRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.physicswallah.assignment.ui.model.CharacterResponse
import com.physicswallah.assignment.ui.retrofit.Resource
import kotlinx.coroutines.launch

class CharacterViewModel(val repo: CharacterRepo):ViewModel() {
    // get character list
    private val listLiveData : MutableLiveData<Resource<CharacterResponse>> = MutableLiveData()
    val listResponse : LiveData<Resource<CharacterResponse>>
        get() = listLiveData

    fun getList() = viewModelScope.launch {
        listLiveData.value = Resource.Loading
        listLiveData.value = repo.getList()
    }

}