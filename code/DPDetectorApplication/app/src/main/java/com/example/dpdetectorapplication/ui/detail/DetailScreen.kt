package com.example.dpdetectorapplication.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dpdetectorapplication.data.model.Impact
import com.example.dpdetectorapplication.ui.components.ExpandableCard
import java.text.SimpleDateFormat
import java.util.Locale
import coil.compose.AsyncImage
import java.io.File

private val datumFormatter =
    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: Int?,
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val detectieState by viewModel.detectie.collectAsState()
    val context = LocalContext.current
    val pattern = viewModel.pattern

    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.loadDetectie(id)
        }
    }

    if (detectieState == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Detectie",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBack
                        ) {
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
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Detectie niet gevonden"
                )
            }
        }

        return
    }

    val detectie = detectieState ?: return

    var uitlegOpen by remember {
        mutableStateOf(false)
    }

    var gevolgenOpen by remember {
        mutableStateOf(false)
    }

    var afbeeldingOpen by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = detectie.patroonNaam,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        if (!detectie.gelezen) {
                            Text(
                                text = "Nieuw",
                                fontSize = 10.sp,
                                lineHeight = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = colorScheme.primary,
                                modifier = Modifier
                                    .background(
                                        color = colorScheme.primary.copy(
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
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
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
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    start = 23.dp,
                    end = 23.dp,
                    bottom = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Datum
            Text(
                text = "${detectie.streamingdienst} • Gedetecteerd op ${
                    datumFormatter.format(detectie.datumTijd)
                }",
                fontSize = 12.sp,
                color = colorScheme.onBackground.copy(
                    alpha = 0.5f
                ),
                modifier = Modifier.padding(
                    start = 20.dp
                )
            )

            // Screenshot
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
            ) {
                AsyncImage(
                    model = File(
                        context.filesDir,
                        "detections/${detectie.afbeelding}"
                    ),
                    contentDescription = "Screenshot van de gedetecteerde interface",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            afbeeldingOpen = true
                        },
                    contentScale = ContentScale.Crop
                )

                // Vergrootknop
                IconButton(
                    onClick = {
                        afbeeldingOpen = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(
                            color = colorScheme.surface.copy(
                                alpha = 0.85f
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Fullscreen,
                        contentDescription = "Afbeelding vergroten"
                    )
                }
            }

            // Beschrijving
            Text(
                text = detectie.beschrijvingDetectie,
                style = typography.bodyMedium,
                lineHeight = 20.sp,
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 10.dp,
                )
            )

            // Zekerheid + impact
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    start = 18.dp,
                    end = 18.dp
                )
            ) {

                // Zekerheid
                Text(
                    text = "Zekerheid: ${detectie.zekerheid}%",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .background(
                            color = colorScheme.onBackground.copy(
                                alpha = 0.10f
                            ),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 5.dp
                        )
                )

                // Impact
                Text(
                    text = "Impact: ${detectie.impact.displayName}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (detectie.impact) {
                        Impact.HOOG ->
                            colorScheme.onError

                        Impact.GEMIDDELD ->
                            androidx.compose.ui.graphics.Color(0xFFE65100)

                        Impact.LAAG ->
                            androidx.compose.ui.graphics.Color(0xFF2E7D32)
                    },
                    modifier = Modifier
                        .background(
                            color = when (detectie.impact) {
                                Impact.HOOG ->
                                    colorScheme.error

                                Impact.GEMIDDELD ->
                                    androidx.compose.ui.graphics.Color(0xFFFFE0B2)

                                Impact.LAAG ->
                                    androidx.compose.ui.graphics.Color(0xFFC8E6C9)
                            },
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 5.dp
                        )
                )
            }

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            if (pattern != null) {

                // Uitleg
                ExpandableCard(
                    title = "Wat is een ${pattern.naam}?",
                    expanded = uitlegOpen,
                    onClick = {
                        uitlegOpen = !uitlegOpen
                    }
                ) {
                    Text(
                        text = pattern.uitleg,
                        style = typography.bodyMedium,
                        lineHeight = 20.sp
                    )
                }

                // Gevolgen
                ExpandableCard(
                    title = "Mogelijke gevolgen",
                    expanded = gevolgenOpen,
                    onClick = {
                        gevolgenOpen = !gevolgenOpen
                    }
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        pattern.gevolgen.forEach { gevolg ->
                            Row(
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "•",
                                    modifier = Modifier.padding(end = 8.dp),
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = gevolg,
                                    style = typography.bodyMedium,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Vergrote afbeelding
    if (afbeeldingOpen) {
        Dialog(
            onDismissRequest = {
                afbeeldingOpen = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        colorScheme.scrim.copy(
                            alpha = 0.80f
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {

                // Afbeelding
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                ) {
                    AsyncImage(
                        model = File(
                            context.filesDir,
                            "detections/${detectie.afbeelding}"
                        ),
                        contentDescription = "Vergrote screenshot",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Fit
                    )

                    // Verkleinknop
                    IconButton(
                        onClick = {
                            afbeeldingOpen = false
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 16.dp, end = 24.dp)
                            .background(
                                color = colorScheme.surface.copy(
                                    alpha = 0.70f
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.FullscreenExit,
                            contentDescription = "Afbeelding verkleinen"
                        )
                    }
                }
            }
        }
    }
}
