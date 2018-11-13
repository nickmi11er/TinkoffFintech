package ru.nickmiller.tinkofffintech.data.repository

import io.reactivex.Observable
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.utils.NetworkUtil


class ProfileRepository(val api: Api, val networkUtil: NetworkUtil) {

    fun getUserProfile() = networkUtil.isConnected()
        .flatMap { connected ->
            if (connected) {
                api.getUserProfile()
                    .flatMap {
                        if (it.status == "Ok") Observable.just(Resource.success(it.user))
                        else Observable.just(Resource.error(RuntimeException("Ошибка сервера")))
                    }
            } else Observable.just(Resource.error(RuntimeException("Отсутствует подключение к сети")))
        }

}