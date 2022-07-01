package com.sergiolopez.domain.model

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val exception: FailException) : Resource<Nothing>()
}

sealed class FailException(val exceptionMessage: String? = null) {
    object NoConnectionAvailable : FailException()
    class BadRequest(message: String) : FailException(message)
    class EmptyBody(message: String) : FailException(message)
}