package fr.isen.giusiano.isensmartcompanion.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.giusiano.isensmartcompanion.EventDetailActivity
import fr.isen.giusiano.isensmartcompanion.api.RetrofitInstance
import fr.isen.giusiano.isensmartcompanion.model.Event
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val events = remember { mutableStateOf<List<Event>>(listOf()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = RetrofitInstance.api.getEvents()
                if (response.isSuccessful) {
                    events.value = response.body() ?: listOf()
                } else {
                    Log.e("EventsScreen", "API Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("EventsScreen", "Exception: ${e.message}")
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(events.value) { event ->
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
