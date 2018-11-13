package ru.nickmiller.tinkofffintech.data.repository

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.entity.ApiResponse
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.utils.NetworkUtil
import java.lang.RuntimeException


class LoginRepository(val api: Api, val networkUtil: NetworkUtil) {

    fun signin(email: String, passwd: String): Observable<Resource<Profile>> =
        networkUtil.isConnected()
            .flatMap { connected ->
                if (connected) api.signin(email, passwd).map {
                    Resource.success(it)
                }
                else Observable.just(Resource.error(RuntimeException("Отсутствует подключение к сети")))
            }


    fun signout() = MutableLiveData<Resource<Any>>().apply {
        api.signout().enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                postValue(Resource.error(t))
            }

            override fun onResponse(call: Call<ApiResponse>, resp: Response<ApiResponse>) {
                val status = resp.code()
            }
        })
    }

}