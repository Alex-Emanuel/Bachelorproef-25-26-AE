package com.example.dpdetectorapplication.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dpdetectorapplication.data.repository.DetectieRepository
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.runtime.LaunchedEffect

private val datumFormatter =
    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: String?,
    onBack: () -> Unit
) {
    val detection = id?.let {
        DetectieRepository.getDetection(it)
    }

    LaunchedEffect(id) {
        if (id != null) {
            DetectieRepository.markAsRead(id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = detection?.titel ?: "Onbekende detectie",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Terug"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            if (detection != null) {

                Text(
                    text = detection.titel,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = detection.zekerheid,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = detection.impact,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = datumFormatter.format(detection.datumTijd),
                    style = MaterialTheme.typography.bodyMedium
                )

            } else {

                Text(
                    text = "Detectie niet gevonden",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}