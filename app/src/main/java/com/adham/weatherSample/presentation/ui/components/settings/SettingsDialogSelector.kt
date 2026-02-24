package com.adham.weatherSample.presentation.ui.components.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adham.weatherSample.domain.models.SettingsParamsModel

@Composable
fun <T> SettingsDialogSelector(
    params: SettingsParamsModel,
    currentItem: T,
    onItemSelected: (T) -> Unit,
    items: List<T>,
    itemToString: @Composable RowScope.(T) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    SettingsCard(
        params = params,
        onClick = {
            showDialog = true
        },
    ) {
        TextButton(onClick = {
            showDialog = true
        }) {
            itemToString(currentItem)
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(params.title) },
            text = {
                Column {
                    items.forEach { item ->
                        TextButton(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Start),
                            onClick = {
                                onItemSelected(item)
                                showDialog = false
                            },
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart,
                            ) {
                                this@TextButton.itemToString(item)
                            }
                        }
                    }
                }
            },
            confirmButton = {},
        )
    }
}
