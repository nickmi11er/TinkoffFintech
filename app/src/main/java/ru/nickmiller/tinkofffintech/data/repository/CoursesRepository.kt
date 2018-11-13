package ru.nickmiller.tinkofffintech.data.repository

import io.reactivex.Observable
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.utils.NetworkUtil


class CoursesRepository(val api: Api, val networkUtil: NetworkUtil) {

    fun getCourses() = networkUtil.isConnected()
        .flatMap { connected ->
            if (connected) {
                api.getCourses().map {
                    Resource.success(it)
                }
            } else {
                Observable.just(Resource.error(RuntimeException("Отсутствует подключение к сети")))
            }
        }

}