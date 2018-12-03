package ru.nickmiller.tinkofffintech


open class BaseException(code: Int = 600, message: String? = "Непредвиденная ошибка") : Throwable(message) {
    constructor(t: Throwable?) : this(message = t?.message)
}



class ConnectionIsNotAvailable : BaseException(601, "Отсутствует подключение к сети")

class AuthorizationError : BaseException(602, "Ошибка авторизации")

class InternalServerError : BaseException(603, "Ошибка сервера")

class EmptyDbTableException : BaseException(604, "Данные не найдены")


 
