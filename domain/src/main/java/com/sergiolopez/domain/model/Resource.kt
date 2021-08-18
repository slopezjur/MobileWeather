package com.sergiolopez.domain.model

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val exception: FailException) : Resource<Nothing>()
}

sealed class FailException {
    object NoConnectionAvailable : FailException()
    object BadRequest : FailException()
    object EmptyBody : FailException()
}