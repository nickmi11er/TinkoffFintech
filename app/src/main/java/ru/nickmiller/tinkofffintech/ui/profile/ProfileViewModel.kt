package ru.nickmiller.tinkofffintech.ui.profile

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.data.repository.ProfileRepository
import ru.nickmiller.tinkofffintech.ui.base.DataViewModel


class ProfileViewModel(val repo: ProfileRepository) : DataViewModel<Profile>() {

    init {
        getProfile()
    }

    fun getProfile(refresh: Boolean = false) {
        if (!isLoading()) {
            disposable = repo.getUserProfile(refresh)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { if (!refresh) loadingObservable.postValue(true) }
                .subscribe(
                    { postData(it) },
                    { postError(it) }
                )
        }
    }
}