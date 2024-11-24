package com.kaizen.gaming.task.domain.model.remote

import com.google.gson.annotations.SerializedName

data class SportResponse(
    @SerializedName("i") val identifier: String,
    @SerializedName("d") val description: String,
    @SerializedName("e") val events: List<EventResponse>
)