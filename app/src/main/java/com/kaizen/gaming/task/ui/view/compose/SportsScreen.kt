package com.kaizen.gaming.task.ui.view.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaizen.gaming.task.R
import com.kaizen.gaming.task.domain.model.local.EventDetail
import com.kaizen.gaming.task.domain.model.local.Sports
import com.kaizen.gaming.task.ui.theme.SportsTheme
import com.kaizen.gaming.task.ui.viewmodel.SportsViewModel
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsScreen(viewModel: SportsViewModel = hiltViewModel()) {

    val sports by viewModel.sports.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchSports()
        viewModel.loadFavorites()
    }

    SportsTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) }
                )
            }
        ) { innerPadding ->
            when {
                errorMessage != null -> {
                    ErrorScreen(
                        errorMessage = errorMessage.orEmpty(),
                        onRetry = { viewModel.fetchSports() }
                    )
                }
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
                else -> {
                    SportsList(
                        sports = sports,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

@Composable
fun SportsList(sports: List<Sports>, viewModel: SportsViewModel, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sports) { sport ->
            SportItem(sport = sport, viewModel = viewModel)
        }
    }
}

@Composable
fun SportItem(sport: Sports, viewModel: SportsViewModel) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = sport.detail.emoji,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = sport.detail.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = sport.showFavoritesOnly,
                        onCheckedChange = { viewModel.toggleShowFavorites(sport.identifier) }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    val rotationAngle by animateFloatAsState(
                        targetValue = if (isExpanded) 180f else 0f,
                        animationSpec = tween(durationMillis = 300)
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        modifier = Modifier
                            .size(24.dp)
                            .graphicsLayer(rotationZ = rotationAngle)
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
            ) {
                val eventsToShow = if (sport.showFavoritesOnly) {
                    sport.events.filter { viewModel.isFavorite(it.id) }
                } else {
                    sport.events
                }
                Box(modifier = Modifier.height(200.dp)) {
                    EventsGrid(events = eventsToShow, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun EventsGrid(
    events: List<EventDetail>,
    viewModel: SportsViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(events) { eventDetail ->
            EventCard(eventDetail = eventDetail, viewModel = viewModel)
        }
    }
}

@Composable
fun EventCard(
    eventDetail: EventDetail,
    viewModel: SportsViewModel
) {
    val eventTimestamp = eventDetail.timestamp * 1000

    var remainingTime by remember { mutableLongStateOf(eventTimestamp - System.currentTimeMillis()) }

    val isFavorited = viewModel.isFavorite(eventDetail.id)

    LaunchedEffect(remainingTime) {
        if (remainingTime > 0) {
            delay(1000)
            remainingTime = eventTimestamp - System.currentTimeMillis()
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = eventDetail.shortDescription,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = getRemainingTime(remainingTime),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )

            IconButton(onClick = { viewModel.toggleFavorite(eventDetail) }) {
                Icon(
                    imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorited) "Unfavorite" else "Favorite",
                    tint = if (isFavorited) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun getRemainingTime(remainingTime: Long) = when {
    remainingTime <= 0 -> {
        if (remainingTime + 1000 > 0) stringResource(R.string.event_live) else stringResource(R.string.event_done)
    }

    else -> {
        val days = TimeUnit.MILLISECONDS.toDays(remainingTime)
        val hours = TimeUnit.MILLISECONDS.toHours(remainingTime) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60

        "${days}d ${hours}h ${minutes}m ${seconds}s"
    }
}


@Preview(showBackground = true)
@Composable
fun SportsAppPreview() {
    SportsScreen()
}
