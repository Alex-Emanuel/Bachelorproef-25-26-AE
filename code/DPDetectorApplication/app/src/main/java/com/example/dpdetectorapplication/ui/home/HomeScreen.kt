package com.example.dpdetectorapplication.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class HomeTab {
    Detecties,
    Instellingen
}

@Composable
fun HomeScreen(
    onItemClick: (String) -> Unit
) {
    var selectedTab by rememberSaveable {
        mutableStateOf(HomeTab.Detecties)
    }

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ) {
        var isActive by rememberSaveable { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 24.dp,
                    bottom = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DP Detector",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = if (isActive) "Actief" else "Inactief",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = isActive,
                onCheckedChange = { actief ->
                    isActive = actief

                    if (actief) {
                        Log.d("DPDetector", "Detector is geactiveerd")
                    } else {
                        Log.d("DPDetector", "Detector is gedeactiveerd")
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = colorScheme.tertiary,

                    uncheckedThumbColor = colorScheme.onBackground,
                    uncheckedTrackColor = Color.LightGray.copy(alpha = 0.5f)
                )
            )
        }

        PrimaryTabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = colorScheme.background,
            contentColor = colorScheme.onBackground,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        selectedTabIndex = selectedTab.ordinal,
                        matchContentSize = false
                    ),
                    width = Dp.Unspecified,
                    height = 2.dp,
                    color = colorScheme.onBackground
                )
            }
        ) {
            Tab(
                selected = selectedTab == HomeTab.Detecties,
                onClick = {
                    selectedTab = HomeTab.Detecties
                },
                text = {
                    Text(
                        text = "Detecties",
                        fontWeight = FontWeight.Bold
                    )
                }
            )

            Tab(
                selected = selectedTab == HomeTab.Instellingen,
                onClick = {
                    selectedTab = HomeTab.Instellingen
                },
                text = {
                    Text(
                        text = "Instellingen",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }

        when (selectedTab) {
            HomeTab.Detecties -> {
                DetectiesScreen(
                    onItemClick = onItemClick,
                    onDisclaimerClick = {
                        // TODO: disclaimer openen
                    },
                    onOpenWidgetClick = {
                        // TODO: widget openen
                    }
                )
            }

            HomeTab.Instellingen -> {
                InstellingenScreen()
            }
        }
    }
}