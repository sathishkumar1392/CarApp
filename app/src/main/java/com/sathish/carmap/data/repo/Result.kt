package com.sathish.carmap.data.repo

import com.sathish.carmap.utils.ErrorResponse


sealed class Result<out T> {

    data class Success<out T>(val value: T) : Result<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
        Result<Nothing>()

    object NetworkError : Result<Nothing>()
}


