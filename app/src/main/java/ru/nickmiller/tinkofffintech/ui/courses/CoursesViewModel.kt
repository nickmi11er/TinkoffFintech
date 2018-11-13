package ru.nickmiller.tinkofffintech.ui.courses

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.entity.course.CoursesResponse
import ru.nickmiller.tinkofffintech.data.repository.CoursesRepository


class CoursesViewModel(val repo: CoursesRepository) : ViewModel() {
    private var disposable: Disposable? = null
    val coursesObservable = MutableLiveData<Resource<CoursesResponse>>()

    init {
        getCourses()
    }

    private fun getCourses() {
        disposable = repo.getCourses()
            .doOnSubscribe { coursesObservable.postValue(Resource.loading()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                coursesObservable.postValue(it)
            }, {
                coursesObservable.postValue(Resource.error(it))
            })
    }


    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

}