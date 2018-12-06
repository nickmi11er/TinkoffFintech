package ru.nickmiller.tinkofffintech.ui.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import ru.nickmiller.tinkofffintech.BaseException
import ru.nickmiller.tinkofffintech.DataNotFoundException
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.utils.SingleLiveEvent


open class DataViewModel<T> : ViewModel() {
    protected var disposables = CompositeDisposable()
    protected var disposable: Disposable? = null

    val errorsObservable = SingleLiveEvent<BaseException>()
    val loadingObservable = MutableLiveData<Boolean>()
    var dataObservable = MutableLiveData<T>()

    fun isLoading() = loadingObservable.value ?: false

    fun postError(error: BaseException?) {
        loadingObservable.postValue(false)
        errorsObservable.postValue(error)
    }

    fun postError(t: Throwable?) {
        if (t is HttpException) {
            val code = t.code()
            if (code == 404) {
                postError(DataNotFoundException())
                return
            }
        }
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


    fun postLoading(progress: Boolean, refresh: Boolean = false) {
        if (progress) {
            if (!refresh) loadingObservable.postValue(true)
        } else {
            loadingObservable.postValue(false)
        }
    }


    override fun onCleared() {
        disposables.clear()
        disposable?.dispose()
        super.onCleared()
    }
}