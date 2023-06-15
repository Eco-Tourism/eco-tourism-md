package com.id.etourism.data.network


import com.google.gson.JsonObject
import com.id.etourism.data.network.response.PostLoginResponse
import com.id.etourism.data.network.response.PostRegisterResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {


    @POST("auth/signin")
    suspend fun postLogin(
        @Body body: JsonObject
    ): Response<PostLoginResponse>

    @POST("auth/signup")
    suspend fun postRegister(
        @Body body: JsonObject
    ): Response<PostRegisterResponse>


}
