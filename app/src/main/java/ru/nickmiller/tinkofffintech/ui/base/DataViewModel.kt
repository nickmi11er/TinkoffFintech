package ru.nickmiller.tinkofffintech.ui.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import ru.nickmiller.tinkofffintech.BaseException
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.utils.SingleLiveEvent


open class DataViewModel<T> : ViewModel() {
    protected var disposable: Disposable? = null

    val errorsObservable = SingleLiveEvent<BaseException>()
    val loadingObservable = MutableLiveData<Boolean>()
    val dataObservable = MutableLiveData<T>()

    fun isLoading() = loadingObservable.value ?: false

    fun postError(error: BaseException?) {
        loadingObservable.postValue(false)
        errorsObservable.postValue(error)
    }

    fun postError(t: Throwable?) {
        t?.printStackTrace()
        postError(BaseException(t))
    }

    fun postData(resource: Resource<T>?) {
        loadingObservable.postValue(false)
        if (resource?.status != Resource.Status.ERROR) {
            resource?.let { dataObservable.postValue(it.data) }
        } else {
            postError(resource.error)
        }
    }


    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}