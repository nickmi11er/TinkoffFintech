package ru.nickmiller.tinkofffintech.ui.base

import android.arch.lifecycle.LiveData


abstract class BaseRepository<T> {
    var dataObservable: LiveData<T>? = null
}