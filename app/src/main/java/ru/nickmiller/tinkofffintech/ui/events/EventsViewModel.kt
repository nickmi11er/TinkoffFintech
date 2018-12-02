package ru.nickmiller.tinkofffintech.ui.events

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.tinkofffintech.EmptyDbTableException
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.data.repository.EventsRepository
import ru.nickmiller.tinkofffintech.ui.base.DataViewModel


class EventsViewModel(val repo: EventsRepository) : DataViewModel<List<Event>>() {

    init {
        getEvents()
    }

    fun getEvents(refresh: Boolean = false) {
        if (!isLoading()) {
            disposable = repo.getEvents(refresh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { if (!refresh) loadingObservable.postValue(true) }
                .subscribe(
                    { postData(it) },
                    { postError(it) }
                )
        }
    }
}