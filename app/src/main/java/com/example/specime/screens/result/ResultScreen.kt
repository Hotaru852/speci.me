package com.example.specime.screens.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.specime.screens.result.components.DataChart

@Composable
fun ResultsScreen(
    viewModel: ResultViewModel = hiltViewModel()
) {
    val state = viewModel.state

    BackHandler(enabled = true) {}

    val traitColors = mapOf(
        "D" to Color(0xFFFF4D4D),
        "I" to Color(0xFFFFD700),
        "S" to Color(0xFF4CAF50),
        "C" to Color(0xFF2196F3)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp)
            )
        } else {
            if (state.testTaken) {
                val text = buildAnnotatedString {
                    append("Kết quả phân tích ")
                    append(
                        AnnotatedString(
                            text = "D",
                            spanStyle = SpanStyle(
                                color = Color(0xFFFF4D4D)
                            )
                        )
                    )
                    append(
                        AnnotatedString(
                            text = "I",
                            spanStyle = SpanStyle(
                                color = Color(0xFFFFD700)
                            )
                        )
                    )
                    append(
                        AnnotatedString(
                            text = "S",
                            spanStyle = SpanStyle(
                                color = Color(0xFF4CAF50)
                            )
                        )
                    )
                    append(
                        AnnotatedString(
                            text = "C",
                            spanStyle = SpanStyle(
                                color = Color(0xFF2196F3)
                            )
                        )
                    )
                }

                Text(
                    text,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 50.dp)
                )
                Spacer(modifier = Modifier.height(50.dp))
                Row {
                    Text(
                        "Bạn thuộc nhóm tính cách ",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    state.personality?.forEach { trait ->
                        traitColors[trait.toString()]?.let {
                            Text(
                                text = trait.toString(),
                                color = it,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
                state.discDescriptions[state.personality]?.let {
                    Text(
                        text = "Bạn là $it",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                state.result?.let {
                    DataChart(
                        result = it,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            } else {
                Text(
                    "Bạn chưa có kết quả nào",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}