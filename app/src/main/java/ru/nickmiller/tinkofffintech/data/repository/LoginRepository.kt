package ru.nickmiller.tinkofffintech.data.repository

import io.reactivex.Observable
import ru.nickmiller.tinkofffintech.ConnectionIsNotAvailable
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.cache.AppDatabase
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.utils.NetworkUtil


class LoginRepository(val api: Api, val db: AppDatabase, val networkUtil: NetworkUtil) {

    fun signin(email: String, passwd: String): Observable<Resource<Profile>> =
        networkUtil.isConnected()
            .flatMap { connected ->
                if (connected)
                    api.signin(email, passwd)
                        .doOnNext {
                            db.profileDao().saveProfile(
                                it.apply { lastUpdatedTime = System.currentTimeMillis() }
                            )
                        }
                        .map { Resource.success(it) }
                else Observable.just(Resource.error(ConnectionIsNotAvailable()))
            }


//    fun signout() = MutableLiveData<Resource<Any>>().apply {
//        api.signout().enqueue(object : Callback<ApiResponse> {
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                postValue(Resource.error(t))
//            }
//
//            override fun onResponse(call: Call<ApiResponse>, resp: Response<ApiResponse>) {
//                val status = resp.code()
//            }
//        })
//    }

}