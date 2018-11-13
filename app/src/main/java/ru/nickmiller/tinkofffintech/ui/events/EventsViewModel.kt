package ru.nickmiller.tinkofffintech.ui.events

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.data.entity.event.Events
import ru.nickmiller.tinkofffintech.data.repository.EventsRepository


class EventsViewModel(val repo: EventsRepository) : ViewModel() {
    private var disposable: Disposable? = null
    val eventsObservable = MutableLiveData<Resource<List<Event>>>()

    init {
        getEvents()
    }

    fun getEvents() {
        if (eventsObservable.value?.status != Resource.Status.LOADING) {
            disposable = repo.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { eventsObservable.postValue(Resource.loading()) }
                .subscribe({
                    eventsObservable.postValue(it)
                }, {
                    eventsObservable.postValue(Resource.error(it))
                })
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}