package com.kaizen.gaming.task.domain.mapper

import com.kaizen.gaming.task.domain.model.local.EventDetail
import com.kaizen.gaming.task.domain.model.local.SportDetail
import com.kaizen.gaming.task.domain.model.local.Sports
import com.kaizen.gaming.task.domain.model.remote.EventResponse
import com.kaizen.gaming.task.domain.model.remote.SportResponse

class SportMapper {
    private val sports = mapOf(
        "FOOT" to SportDetail("Football", "⚽"),
        "BASK" to SportDetail("Basketball", "🏀"),
        "TENN" to SportDetail("Tennis", "🎾"),
        "TABL" to SportDetail("Table Tennis", "🏓"),
        "VOLL" to SportDetail("Volleyball", "🏐"),
        "ESPS" to SportDetail("Esports", "🎮"),
        "ICEH" to SportDetail("Ice Hockey", "🏒"),
        "HAND" to SportDetail("Handball", "🤾"),
        "SNOO" to SportDetail("Snooker", "🎱"),
        "FUTS" to SportDetail("Futsal", "🦶"),
        "DART" to SportDetail("Darts", "🎯")
    )

    fun mapToSports(sportResponse: List<SportResponse>): List<Sports> =
        sportResponse.map { sport ->
            Sports(
                detail = getSport(sport.identifier),
                identifier = sport.identifier,
                events = mapToEventDetail(sport.events)
            )
        }

    fun getSport(identifier: String): SportDetail {
        return sports[identifier] ?: SportDetail("Unknown Sport", "❓")
    }

    fun mapToEventDetail(eventResponse: List<EventResponse>): List<EventDetail> =
        eventResponse.map { event ->
            EventDetail(
                id = event.identifier,
                shortDescription = event.shortDescription,
                timestamp = event.timestamp,
                isFavorite = false
            )
        }
}