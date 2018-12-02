package ru.nickmiller.tinkofffintech.data

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import ru.nickmiller.tinkofffintech.data.entity.ApiResponse
import ru.nickmiller.tinkofffintech.data.entity.course.CourseAbout
import ru.nickmiller.tinkofffintech.data.entity.course.CourseInfo
import ru.nickmiller.tinkofffintech.data.entity.course.CoursesResponse
import ru.nickmiller.tinkofffintech.data.entity.event.EventInfo
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

    @GET
    fun getEventInfo(@Url url: String): Observable<EventInfo>

    @GET("user")
    fun getUserProfile(): Observable<UserResponse>

    @GET("connections")
    fun getCourses(): Observable<CoursesResponse>

    @GET
    fun getCourseInfo(@Url url: String): Observable<List<CourseInfo>>

    @GET
    fun getCourseAbout(@Url url: String): Observable<CourseAbout>
}
 
 
