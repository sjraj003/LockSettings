package com.lock.settings.network.api

import com.lock.settings.network.model.LockConfigModel
import retrofit2.http.GET
import retrofit2.http.Headers


interface LockConfigurationService {
    @Headers("Accept: application/json")
    @GET("v3/d5f5d613-474b-49c4-a7b0-7730e8f8f486/")
    suspend fun getLockConfiguration(): LockConfigModel
}