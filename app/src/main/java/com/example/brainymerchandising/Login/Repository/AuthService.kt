package com.example.brainymerchandising.Login.Repository


import com.example.brainymerchandising.Login.Model.SignInResponse
import com.example.brainymerchandising.Login.Model.UserInfo_LoginPost
import com.example.brainymerchandising.Login.Model.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body signinObject: UserInfo_LoginPost) : Response<SignInResponse>

    @GET("/user/{user_username}")
    suspend fun getUser(@Path("user_username") username: String?): Response<UserResponse>
/*
    @Multipart
    @POST("/user")
    suspend fun changeProfile(@Part file: MultipartBody.Part?, @Part("user") user: RequestBody):Response<EditProfileResponse>
*/
}

