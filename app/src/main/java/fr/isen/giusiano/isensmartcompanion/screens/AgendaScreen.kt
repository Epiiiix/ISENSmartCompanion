package fr.isen.giusiano.isensmartcompanion.screens

import android.icu.util.Calendar
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.giusiano.isensmartcompanion.R

@Composable
fun AgendaScreen() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var currentYear by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var currentMonth by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val monthNames = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    val today = Calendar.getInstance()
    val daysInMonth = remember(currentYear, currentMonth) {
        calendar.set(currentYear, currentMonth, 1)
        calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    val firstDayOfMonth = remember(currentYear, currentMonth) {
        calendar.set(currentYear, currentMonth, 1)
        (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (currentMonth == 0) {
                    currentMonth = 11
                    currentYear--
                } else {
                    currentMonth--
                }
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.previous_month))
            }
            Text(
                text = "${monthNames[currentMonth]} $currentYear",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = {
                if (currentMonth == 11) {
                    currentMonth = 0
                    currentYear++
                } else {
                    currentMonth++
                }
            }) {
                Icon(Icons.Default.ArrowForward, contentDescription = stringResource(R.string.next_month))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.black)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            var dayCounter = 1 - firstDayOfMonth
            for (row in 0 until 6) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (col in 0 until 7) {
                        if (dayCounter in 1..daysInMonth) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(100.dp)
                                    .background(
                                        if (dayCounter == today.get(Calendar.DAY_OF_MONTH) &&
                                            currentMonth == today.get(Calendar.MONTH) &&
                                            currentYear == today.get(Calendar.YEAR)
                                        ) colorResource(R.color.red) else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        Toast.makeText(context, "Not Implemented Yet!", Toast.LENGTH_SHORT).show()
                                    },
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "$dayCounter",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (dayCounter == today.get(Calendar.DAY_OF_MONTH) &&
                                            currentMonth == today.get(Calendar.MONTH) &&
                                            currentYear == today.get(Calendar.YEAR)
                                        ) colorResource(R.color.white) else colorResource(R.color.black)
                                    )
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f).aspectRatio(1.2f))
                        }
                        dayCounter++
                    }
                }
            }
        }
    }
}

