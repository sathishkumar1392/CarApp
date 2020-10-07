package com.sathish.carmap.utils

data class ErrorResponse(
    val error_description: String,
    val causes: Map<String, String> = emptyMap()
)

