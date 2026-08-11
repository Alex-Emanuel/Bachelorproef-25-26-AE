package com.example.dpdetectorapplication.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dpdetectorapplication.data.model.Detectie
import com.example.dpdetectorapplication.data.repository.DetectieRepository
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun DetectiesScreen(
    onItemClick: (String) -> Unit,
    onDisclaimerClick: () -> Unit,
    onOpenWidgetClick: () -> Unit
) {
    val detections = DetectieRepository.getDetections()

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.background)
                    .navigationBarsPadding()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                verticalAlignment = Alignment.Bottom
            ) {

                // Disclaimer + info icon
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable { onDisclaimerClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Disclaimer",
                        modifier = Modifier.size(16.dp),
                        tint = colorScheme.onBackground.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Disclaimer",
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline,
                        color = colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Open widget
                Button(
                    onClick = onOpenWidgetClick
                ) {
                    Text(
                        text = "Open widget",
                        fontSize = 13.sp
                    )
                }
            }
        }
    ) { innerPadding ->

        if (detections.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    modifier = Modifier.size(84.dp).padding(bottom = 24.dp),
                    tint = Color.LightGray
                )

                Text(
                    text = "Geen detecties",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Zodra een dark pattern wordt gedetecteerd, verschijnt het hier.\n" +
                            "Je kan zelf handmatig een nieuwe analyse starten via de zwevende actieknop. Klik hiervoor op de \"Open Widget\"-knop",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(250.dp)
                )
            }
        } else {
            LazyColumn {
                items(detections) { detection ->
                    DetectieRow(
                        detection = detection,
                        onClick = {
                            onItemClick(detection.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DetectieRow(
    detection: Detectie,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = detection.title,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = detection.certainty,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = detection.impact,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = detection.dateTime,
            style = MaterialTheme.typography.bodySmall
        )
    }
}