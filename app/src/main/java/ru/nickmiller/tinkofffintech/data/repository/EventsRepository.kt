package ru.nickmiller.tinkofffintech.data.repository

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.data.entity.event.EventInfo
import ru.nickmiller.tinkofffintech.data.entity.event.map
import ru.nickmiller.tinkofffintech.utils.NetworkUtil


class EventsRepository(val api: Api, val networkUtil: NetworkUtil) {

    fun getEvents(): Observable<Resource<List<Event>>> = networkUtil.isConnected()
        .flatMap { connected ->
            if (connected) {
                api.getEvents()
                    .flatMapIterable { it.map() }
                    .flatMap { event ->
                        if (event.isActual && event.url?.isNotEmpty() == true) {
                            Observable.just(event).zipWith(api.getEventInfo("api/event${event.url}"),
                                BiFunction<Event, EventInfo, Event> { t1, t2 ->
                                    t1.apply { eventInfo = t2 }
                                })
                        } else Observable.just(event)
                    }
                    .toList()
                    .map { Resource.success(it) }
                    .toObservable()
            } else Observable.just(Resource.error(RuntimeException("Отсутствует подключение к сети")))
        }
}