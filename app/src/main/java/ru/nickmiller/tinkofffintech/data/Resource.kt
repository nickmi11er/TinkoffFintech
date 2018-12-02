package ru.nickmiller.tinkofffintech.data

import ru.nickmiller.tinkofffintech.BaseException

data class Resource<out T>(val status: Status, val data: T?, val error: BaseException?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(err: BaseException): Resource<T> {
            return Resource(Status.ERROR, null, err)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}
 
 
