package ru.nickmiller.tinkofffintech.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.tinkofffintech.AuthorizationError
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.data.repository.LoginRepository


class LoginViewModel(val repo: LoginRepository) : ViewModel() {
    val loginObserver = MutableLiveData<Resource<Profile>>()
    private var disposable: Disposable? = null

    fun signin(username: String, passwd: String) {
        if (loginObserver.value?.status != Resource.Status.LOADING) {
            disposable = repo.signin(username, passwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loginObserver.postValue(Resource.loading()) }
                .subscribe({ loginObserver.postValue(it) }, {
                    loginObserver.postValue(Resource.error(AuthorizationError()))
                })
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}