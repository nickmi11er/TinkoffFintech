package ru.nickmiller.tinkofffintech.data.repository

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import ru.nickmiller.tinkofffintech.ConnectionIsNotAvailable
import ru.nickmiller.tinkofffintech.EmptyDbTableException
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.cache.AppDatabase
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.data.entity.event.EventInfo
import ru.nickmiller.tinkofffintech.data.entity.event.map
import ru.nickmiller.tinkofffintech.utils.NetworkUtil


class EventsRepository(val api: Api, val db: AppDatabase, val networkUtil: NetworkUtil) {

    fun getEvents(forceUpdate: Boolean = false): Observable<Resource<List<Event>>> =
        if (forceUpdate) {
            getFromCloud()
        } else {
            Observable.concat(
                getFromCache().filter { !it.data.isNullOrEmpty() },
                getFromCloud()
            )
        }


    fun getFromCache() = Observable.fromCallable {
        val events = db.eventsDao().getAllEvents()
        if (events != null) {
            Resource.success(events)
        } else {
            Resource.error(EmptyDbTableException())
        }
    }


    fun getFromCloud() = networkUtil.isConnected()
        .flatMap { connected ->
            if (connected) {
                api.getEvents()
                    .flatMapIterable { it.map() }
                    .flatMap { event ->
                        if (event.isActual && event.url?.isNotEmpty() == true) {
                            Observable.just(event).zipWith(api.getEventInfo("api/event${event.url}"),
                                BiFunction<Event, EventInfo, Event> { event, ei ->
                                    event.apply { eventInfo = ei }
                                }).onErrorReturn { event.apply { url = null } }
                        } else Observable.just(event)
                    }
                    .toList()
                    .map {
                        db.eventsDao().saveEvents(it)
                        Resource.success(it)
                    }
                    .toObservable()
            } else Observable.just(Resource.error(ConnectionIsNotAvailable()))
        }
}