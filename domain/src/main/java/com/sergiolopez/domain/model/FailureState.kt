package com.sergiolopez.domain.model

data class FailureState(
    val failureStateType: FailureStateType,
    val failureMessage: String? = null
)