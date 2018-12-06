package ru.nickmiller.tinkofffintech.ui.profile

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.data.entity.profile.minsFromLastUpdate
import ru.nickmiller.tinkofffintech.data.repository.ProfileRepository
import ru.nickmiller.tinkofffintech.ui.base.DataViewModel
import ru.nickmiller.tinkofffintech.utils.SingleLiveEvent


class ProfileViewModel(val repo: ProfileRepository) : DataViewModel<Profile>() {

    val editProfileObservable = SingleLiveEvent<Resource<Boolean>>()
    fun getDataObservable() = repo.dataObservable


    init {
        val disposable = repo.getProfileFromCache()
            .subscribeOn(Schedulers.computation())
            .doOnSubscribe { postLoading(true) }
            .doOnTerminate { postLoading(false) }
            .subscribe { res ->
                if (res.data != null && shouldUpdate(res.data)) updateProfile()
                else if (res.data == null) updateProfile()
            }
        disposables.add(disposable)
    }

    fun updateProfile(refresh: Boolean = false) {
        val disposable = repo.getProfileFromCloud()
            .subscribeOn(Schedulers.computation())
            .doOnSubscribe { postLoading(true, refresh) }
            .doOnTerminate { postLoading(false, refresh) }
            .subscribe(
                { it.data?.let { repo.cacheProfile(it) } },
                { postError(it) }
            )
        disposables.add(disposable)
    }


    fun editProfile(profile: Profile) {
        val disposable = repo.editUserProfile(profile)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    updateProfile()
                    editProfileObservable.postValue(it)
                },
                { editProfileObservable.postValue(Resource.error(it)) }
            )
        disposables.add(disposable)
    }

    private fun shouldUpdate(profile: Profile) = profile.minsFromLastUpdate() >= 60
}