package com.physicswallah.assignment.ui.viewModelRepo

import com.physicswallah.assignment.ui.base.BaseRepository
import com.physicswallah.assignment.ui.retrofit.APIService

class CharacterRepo(val apiService: APIService) : BaseRepository() {
    suspend fun getList() = safeApiCall {
        apiService.getCharacterList()
    }
}