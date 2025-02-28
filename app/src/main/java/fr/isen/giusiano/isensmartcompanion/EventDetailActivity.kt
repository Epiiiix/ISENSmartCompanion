package fr.isen.giusiano.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.isen.giusiano.isensmartcompanion.model.Event

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val event = intent.getParcelableExtra<Event>("event")

        setContent {
            if (event != null) {
                EventDetailScreen(event)
            } else {
                Text("Event not found", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun EventDetailScreen(event: Event) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = event.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Date: ${event.date}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Location: ${event.location}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Category: ${event.category}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
    }
}
