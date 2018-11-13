package ru.nickmiller.tinkofffintech.ui.profile

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.data.repository.ProfileRepository


class ProfileViewModel(val repo: ProfileRepository) : ViewModel() {
    private var disposable: Disposable? = null
    val profileObservable = MutableLiveData<Resource<Profile>>()

    init {
        getProfile()
    }

    fun getProfile() {
        disposable = repo.getUserProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { profileObservable.postValue(Resource.loading()) }
            .subscribe({
                profileObservable.postValue(it)
            }, {
                profileObservable.postValue(Resource.error(it))
            })
    }


    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

}