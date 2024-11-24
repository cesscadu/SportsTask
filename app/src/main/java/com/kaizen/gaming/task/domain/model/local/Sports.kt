package com.kaizen.gaming.task.domain.model.local

data class Sports(
    val detail: SportDetail,
    val identifier: String,
    val events: List<EventDetail>,
    val originalEvents: List<EventDetail> = events,
    var showFavoritesOnly: Boolean = false
)