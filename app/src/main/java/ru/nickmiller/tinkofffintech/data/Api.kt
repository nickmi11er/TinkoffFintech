package ru.nickmiller.tinkofffintech.data

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import ru.nickmiller.tinkofffintech.data.entity.ApiResponse
import ru.nickmiller.tinkofffintech.data.entity.event.Events
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.data.entity.profile.UserResponse

const val REFERER = "https://fintech.tinkoff.ru/"

interface Api {
    @FormUrlEncoded
    @POST("signin")
    fun signin(@Field("email") email: String, @Field("password") passwd: String): Observable<Profile>

    @POST("signout")
    fun signout(@Header("Referer") referer: String = REFERER): Call<ApiResponse>

    @GET("calendar/list/event")
    fun getEvents(): Observable<Events>

    @GET("user")
    fun getUserProfile(): Call<UserResponse>
}
 
 
