package ru.nickmiller.tinkofffintech.data.repository

import io.reactivex.Observable
import ru.nickmiller.tinkofffintech.BaseException
import ru.nickmiller.tinkofffintech.ConnectionIsNotAvailable
import ru.nickmiller.tinkofffintech.InternalServerError
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.cache.AppDatabase
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.utils.NetworkUtil
import java.util.concurrent.TimeUnit


class ProfileRepository(val api: Api, val db: AppDatabase, val networkUtil: NetworkUtil) {

    fun getUserProfile(refresh: Boolean = false): Observable<Resource<Profile>> =
        if (refresh) {
            getProfileFromCloud()
        } else {
            Observable.fromCallable {
                val profile = db.profileDao().getProfile()
                if (profile != null) Resource.success(profile)
                else Resource.error(BaseException())
            }
                .flatMap { res ->
                    if (res.data != null) {
                        val minsFromLastUpdate =
                            TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - res.data.lastUpdatedTime)
                        if (minsFromLastUpdate >= 60) {
                            Observable.concat(Observable.just(res), getProfileFromCloud())
                        } else {
                            Observable.just(res)
                        }
                    } else {
                        getProfileFromCloud()
                    }
                }
        }


    private fun getProfileFromCloud(): Observable<Resource<Profile>> =
        networkUtil.isConnected()
            .flatMap { connected ->
                if (connected) {
                    api.getUserProfile()
                        .flatMap {
                            if (it.status == "Ok") {
                                db.profileDao().saveProfile(it.user!!.apply {
                                    lastUpdatedTime = System.currentTimeMillis()
                                })
                                Observable.just(Resource.success(it.user))
                            } else Observable.just(Resource.error(InternalServerError()))
                        }
                } else Observable.just(Resource.error(ConnectionIsNotAvailable()))
            }
}