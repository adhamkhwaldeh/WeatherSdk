package com.adham.weatherSample.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adham.weatherSample.LocalAppContext
import com.adham.weatherSample.R
import com.adham.weatherSdk.domain.models.AddressModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

// I added a suppression temporarily in order to focus on implementing the unit tests properly.
@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressListItem(
    address: AddressModel,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onSetDefault: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val context = LocalAppContext.current

    val dismissState =
        rememberSwipeToDismissBoxState(
            SwipeToDismissBoxValue.Settled,
            SwipeToDismissBoxDefaults.positionalThreshold,
        )

    when (dismissState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            showDeleteDialog = true
            true
        }

        SwipeToDismissBoxValue.StartToEnd -> {
            onSetDefault()
            true
        }

        else -> {
            false
        }
    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(context.getString(R.string.delete_address_title)) },
            text = { Text(context.getString(R.string.delete_address_confirmation)) },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    },
                ) {
                    Text(context.getString(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                        }
                        showDeleteDialog = false
                    },
                ) {
                    Text(context.getString(R.string.cancel))
                }
            },
        )
    }
    var showSheet by remember { mutableStateOf(false) }

    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        backgroundContent = {
            val color =
                when (dismissState.dismissDirection) {
                    SwipeToDismissBoxValue.EndToStart -> {
                        Color.Red
                    }

                    SwipeToDismissBoxValue.StartToEnd -> {
                        Color.Green
                    }

                    else -> {
                        Color.Transparent
                    }
                }
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(horizontal = 16.dp),
            ) {
                if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "Default",
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.CenterStart),
                    )
                } else if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.CenterEnd),
                    )
                }
            }
        },
        enableDismissFromStartToEnd = !address.isDefault,
        enableDismissFromEndToStart = !address.isDefault,
    ) {
        ListItem(
            headlineContent = { Text(address.name) },
            leadingContent = {
                IconButton(
                    onClick = {
                        showSheet = true
                    },
                ) {
                    Icon(
                        Icons.Default.LocationCity,
                        contentDescription = null,
                    )
                }
            },
            trailingContent = {
                if (address.isDefault) {
                    OutlinedButton(onClick = {}, shape = ButtonDefaults.outlinedShape) {
                        Text(stringResource(R.string.Default))
                    }
                }
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .testTag("addressItem_${address.name}"),
        )
    }

    LocationViewBottomSheet(
        selectedLocation =
            LatLng(
                address.lat.toDouble(),
                address.lon.toDouble(),
            ),
        showSheet = showSheet,
        onDismiss = {
            showSheet = false
        },
    )
}
