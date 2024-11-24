package com.kaizen.gaming.task.domain.model.remote

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("i") val identifier: String,
    @SerializedName("d") val description: String,
    @SerializedName("tt") val timestamp: Long,
    @SerializedName("si") val sportIdentifier: String,
    @SerializedName("sh") val shortDescription: String
)
