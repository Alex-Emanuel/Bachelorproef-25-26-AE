package com.example.dpdetectorapplication.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dpdetectorapplication.data.model.Detectie
import com.example.dpdetectorapplication.data.model.Impact
import com.example.dpdetectorapplication.data.model.darkPatterns
import com.example.dpdetectorapplication.data.repository.DetectieRepository
import java.text.SimpleDateFormat
import java.util.Locale

private val datumFormatter =
    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

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
                    .background(MaterialTheme.colorScheme.background)
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
                        tint = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.5f
                        )
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Disclaimer",
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.5f
                        )
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
                    modifier = Modifier
                        .size(84.dp)
                        .padding(bottom = 24.dp),
                    tint = Color.LightGray
                )

                Text(
                    text = "Geen detecties",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Zodra een dark pattern wordt gedetecteerd, verschijnt het hier.\n" +
                            "Je kan zelf handmatig een nieuwe analyse starten via de zwevende actieknop. " +
                            "Klik hiervoor op de \"Open widget\"-knop",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(250.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(
                        start = 24.dp,
                        end = 24.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val groepen = DetectieRepository.getDetectionGroups()

                groepen.forEach { (groep, groepDetecties) ->

                    item {
                        Text(
                            text = groep,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.6f
                            ),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    items(groepDetecties) { detection ->
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
}

@Composable
fun DetectieRow(
    detection: Detectie,
    onClick: () -> Unit
) {
    // Zoek de algemene informatie over het gedetecteerde patroon
    val pattern = darkPatterns.find {
        it.id == detection.patroonId
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = detection.afbeeldingResId),
            contentDescription = pattern?.naam,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            // Naam + Nieuw-label
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pattern?.naam ?: detection.patroonId,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                if (!detection.gelezen) {
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Nieuw",
                        fontSize = 10.sp,
                        lineHeight = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.12f
                                ),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(
                                horizontal = 7.dp,
                                vertical = 4.dp
                            )
                    )
                }
            }

            // Zekerheid
            Text(
                text = "Zekerheid: ${detection.zekerheid}%",
                fontSize = 12.sp,
                lineHeight = 12.sp,
                modifier = Modifier.padding(bottom = 3.dp)
            )

            // Impact
            Text(
                text = "Impact: ${detection.impact.displayName}",
                fontSize = 12.sp,
                lineHeight = 12.sp,
                color = when (detection.impact) {
                    Impact.HOOG -> MaterialTheme.colorScheme.error
                    Impact.GEMIDDELD -> MaterialTheme.colorScheme.onBackground
                    Impact.LAAG -> MaterialTheme.colorScheme.onBackground
                },
                modifier = Modifier.padding(bottom = 3.dp)
            )

            // Datum en tijd
            Text(
                text = datumFormatter.format(detection.datumTijd),
                fontSize = 12.sp,
                lineHeight = 12.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.5f
                ),
                modifier = Modifier.padding(bottom = 3.dp)
            )
        }
    }
}