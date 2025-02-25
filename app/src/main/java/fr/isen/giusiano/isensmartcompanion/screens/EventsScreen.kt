package fr.isen.giusiano.isensmartcompanion.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.giusiano.isensmartcompanion.EventDetailActivity
import fr.isen.giusiano.isensmartcompanion.models.Event

@Composable
fun EventsScreen() {
    val context = LocalContext.current
    val events = listOf(
        Event(1, "BDE Evening", "A fun evening organized by the BDE.", "March 10, 2025", "ISEN Campus", "Party"),
        Event(2, "Gala", "Annual ISEN Gala with dinner and awards.", "April 15, 2025", "Grand Hotel", "Formal"),
        Event(3, "Cohesion Day", "A day full of activities to strengthen bonds.", "May 5, 2025", "City Park", "Team Building")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(events) { event ->
            EventItem(event) {
                val intent = Intent(context, EventDetailActivity::class.java).apply {
                    putExtra("event", event)
                }
                context.startActivity(intent)
            }
        }
    }
}

@Composable
fun EventItem(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = event.date, style = MaterialTheme.typography.bodyMedium)
            Text(text = event.location, style = MaterialTheme.typography.bodyMedium)
        }
    }
}