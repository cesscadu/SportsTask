package com.kaizen.gaming.task.mapper

import com.kaizen.gaming.task.domain.mapper.SportMapper
import com.kaizen.gaming.task.domain.model.local.EventDetail
import com.kaizen.gaming.task.domain.model.local.SportDetail
import com.kaizen.gaming.task.domain.model.local.Sports
import com.kaizen.gaming.task.domain.model.remote.EventResponse
import com.kaizen.gaming.task.domain.model.remote.SportResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SportMapperTest {

    private lateinit var sportMapper: SportMapper

    @Before
    fun setup() {
        sportMapper = SportMapper()
    }

    @Test
    fun `mapToSports should correctly map a valid SportResponse list to Sports list`() {
        val sportResponseList = listOf(
            SportResponse(
                description = "description",
                identifier = "FOOT",
                events = listOf(
                    EventResponse(
                        identifier = "event1",
                        shortDescription = "Match 1",
                        timestamp = 1732374551,
                        description = "description",
                        sportIdentifier = "identifier 1"
                    ),
                    EventResponse(
                        identifier = "event2",
                        shortDescription = "Match 2",
                        timestamp = 1732375551,
                        description = "description",
                        sportIdentifier = "identifier 2"
                    )
                )
            )
        )

        val expected = listOf(
            Sports(
                detail = SportDetail(name = "Football", emoji = "⚽"),
                identifier = "FOOT",
                events = listOf(
                    EventDetail(
                        id = "event1",
                        shortDescription = "Match 1",
                        timestamp = 1732374551,
                        isFavorite = false
                    ),
                    EventDetail(
                        id = "event2",
                        shortDescription = "Match 2",
                        timestamp = 1732375551,
                        isFavorite = false
                    )
                )
            )
        )

        val actual = sportMapper.mapToSports(sportResponseList)

        assertEquals(expected, actual)
    }

    @Test
    fun `mapToSports should handle unknown sports gracefully`() {
        val sportResponseList = listOf(
            SportResponse(
                description = "",
                identifier = "UNKNOWN",
                events = listOf(
                    EventResponse(
                        identifier = "event1",
                        shortDescription = "Unknown Match",
                        timestamp = 1732374551,
                        description = "description",
                        sportIdentifier = "identifier 1"
                    )
                )
            )
        )

        val expected = listOf(
            Sports(
                detail = SportDetail(name = "Unknown Sport", emoji = "❓"),
                identifier = "UNKNOWN",
                events = listOf(
                    EventDetail(
                        id = "event1",
                        shortDescription = "Unknown Match",
                        timestamp = 1732374551,
                        isFavorite = false
                    )
                )
            )
        )

        val actual = sportMapper.mapToSports(sportResponseList)

        assertEquals(expected, actual)
    }

    @Test
    fun `mapToEventDetail should correctly map EventResponse to EventDetail`() {
        val eventResponseList = listOf(
            EventResponse(
                identifier = "event1",
                shortDescription = "Match 1",
                timestamp = 1732374551,
                description = "description",
                sportIdentifier = "identifier 1"
            ),
            EventResponse(
                identifier = "event2",
                shortDescription = "Match 2",
                timestamp = 1732375551,
                description = "description",
                sportIdentifier = "identifier 2"
            )
        )

        val expected = listOf(
            EventDetail(
                id = "event1",
                shortDescription = "Match 1",
                timestamp = 1732374551,
                isFavorite = false
            ),
            EventDetail(
                id = "event2",
                shortDescription = "Match 2",
                timestamp = 1732375551,
                isFavorite = false
            )
        )

        val actual = sportMapper.mapToEventDetail(eventResponseList)

        assertEquals(expected, actual)
    }
}
