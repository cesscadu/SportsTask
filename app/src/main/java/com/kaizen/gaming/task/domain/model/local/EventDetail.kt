package com.kaizen.gaming.task.domain.model.local

data class EventDetail(
    val id: String,
    val shortDescription: String,
    val timestamp: Long,
    var isFavorite: Boolean = false
)