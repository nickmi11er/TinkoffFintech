package ru.nickmiller.tinkofffintech.data.repository

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.tinkofffintech.BaseException
import ru.nickmiller.tinkofffintech.ConnectionIsNotAvailable
import ru.nickmiller.tinkofffintech.InternalServerError
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.cache.AppDatabase
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.data.entity.profile.minsFromLastUpdate
import ru.nickmiller.tinkofffintech.ui.base.BaseRepository
import ru.nickmiller.tinkofffintech.utils.NetworkUtil
import ru.nickmiller.tinkofffintech.utils.cookies.CookiesStore


class ProfileRepository(
    val api: Api,
    val db: AppDatabase,
    val networkUtil: NetworkUtil,
    val cookiesStore: CookiesStore
) : BaseRepository<Profile?>() {

    init {
        dataObservable = db.profileDao().getProfileLD()
    }


    fun getProfileFromCloud(): Observable<Resource<Profile>> {
        return networkUtil.isConnected()
            .flatMap { connected ->
                if (connected) {
                    api.getUserProfile().flatMap {
                        if (it.status == "Ok") Observable.just(Resource.success(it.user))
                        else Observable.just(Resource.error(InternalServerError()))
                    }
                } else Observable.just(Resource.error(ConnectionIsNotAvailable()))
            }
    }


    fun cacheProfile(profile: Profile) {
        db.profileDao().saveProfile(profile.apply {
            lastUpdatedTime = System.currentTimeMillis()
        })
    }


    fun getProfileFromCache(): Observable<Resource<Profile>> {
        return Observable.fromCallable {
            val profile = db.profileDao().getProfile()
            if (profile != null) Resource.success(profile)
            else Resource.error(BaseException())
        }
    }


    fun editUserProfile(profile: Profile): Observable<Resource<Boolean>> {
        val cookies = cookiesStore.getCookies()
        var token = cookies?.firstOrNull { it.contains("csrftoken", true) }
        if (token != null) {
            token = token.substringAfter("=").substringBefore(";")
        } else {
            return Observable.just(Resource.error(BaseException(message = "Неверный токен")))
        }

        var allCookies = ""
        cookies?.forEach { allCookies += "$it; " }

        return api.editProfile(
            CSRFToken = token,
            cookie = allCookies,
            profile = profile
        ).map { Resource.success(true) }
    }
}