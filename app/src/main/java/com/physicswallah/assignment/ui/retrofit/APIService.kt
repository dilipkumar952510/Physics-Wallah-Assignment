package com.physicswallah.assignment.ui.retrofit

import com.physicswallah.assignment.ui.model.CharacterResponse
import retrofit2.http.GET

interface APIService {
    @GET("character")
    suspend fun getCharacterList(): CharacterResponse
}