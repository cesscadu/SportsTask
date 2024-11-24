package com.kaizen.gaming.task.data.api

import com.kaizen.gaming.task.domain.model.remote.SportResponse
import retrofit2.http.GET

interface SportsApiService {
    @GET("sports.json")
    suspend fun getSports(): List<SportResponse>
}
