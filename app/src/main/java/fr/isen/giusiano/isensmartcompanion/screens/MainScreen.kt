package fr.isen.giusiano.isensmartcompanion.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.giusiano.isensmartcompanion.R
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    var question by remember { mutableStateOf(TextFieldValue("")) }
    val messages = remember { mutableStateListOf<Pair<String?, Boolean>>() }
    val generativeModel = remember {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = "AIzaSyCaQj0XX8l7p4BCvFPHFpfnLx3aiSZSAv0"
        )
    }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.isen_logo),
            contentDescription = context.getString(R.string.isen_logo),
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Column {
                messages.forEach { (msg, isUser) ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        contentAlignment = if (isUser) Alignment.CenterStart else Alignment.CenterEnd
                    ) {
                        Text(
                            text = msg?: "",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier
                                .background(
                                    if (isUser) colorResource(R.color.teal_700) else colorResource(R.color.grey),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(colorResource(R.color.white))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = question,
                    onValueChange = { question = it },
                    modifier = Modifier.weight(1f),
                    decorationBox = { innerTextField ->
                        if (question.text.isEmpty()) {
                            Text(
                                text = "Ask me a question...",
                                color = colorResource(R.color.grey),
                                fontSize = 18.sp
                            )
                        }
                        innerTextField()
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (question.text.isNotBlank()) {
                            messages.add(question.text to true)
                            question = TextFieldValue("")

                            coroutineScope.launch {
                                try {
                                    val conversationContext = messages.joinToString("\n") { it.first.toString()}
                                    val result = generativeModel.generateContent(conversationContext)
                                    messages.add(result.text to false)
                                } catch (e: Exception) {
                                    messages.add("Error: ${e.message}" to false)
                                }
                            }
                            Toast.makeText(context, "Message envoyé!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(colorResource(R.color.red))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "Send",
                        modifier = Modifier.size(30.dp),
                        tint = colorResource(R.color.white)
                    )
                }
            }
        }
    }
}
