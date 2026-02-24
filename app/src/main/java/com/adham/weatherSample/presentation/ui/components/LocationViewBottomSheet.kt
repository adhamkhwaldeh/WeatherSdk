package com.adham.weatherSample.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adham.weatherSample.R
import com.adham.weatherSample.helpers.AppConstantsHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationViewBottomSheet(
    selectedLocation: LatLng,
    showSheet: Boolean,
    onDismiss: () -> Unit,
) {
    if (!showSheet) return

    val cameraPositionState = rememberCameraPositionState()
    LaunchedEffect(Unit) {
        cameraPositionState.move(
            CameraUpdateFactory.newLatLngZoom(
                selectedLocation,
                AppConstantsHelper.DEFAULT_MAP_ZOOM,
            ),
        )
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState =
            rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
            ),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(500.dp),
        ) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                uiSettings =
                    MapUiSettings(
                        zoomControlsEnabled = true,
                        zoomGesturesEnabled = true,
                        scrollGesturesEnabled = true,
                        tiltGesturesEnabled = true,
                        rotationGesturesEnabled = true,
                        myLocationButtonEnabled = true,
                    ),
                properties =
                    MapProperties(
                        isMyLocationEnabled = true,
                    ),
            ) {
                Marker(
                    state = MarkerState(position = selectedLocation),
                )
            }
        }

        Button(
            onClick = {
                onDismiss()
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Text(stringResource(R.string.Close))
        }
    }
}
