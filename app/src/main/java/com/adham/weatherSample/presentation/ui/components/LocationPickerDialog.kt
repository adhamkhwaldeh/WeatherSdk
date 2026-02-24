package com.adham.weatherSample.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.adham.weatherSample.LocalAppContext
import com.adham.weatherSample.R
import com.adham.weatherSample.helpers.AppConstantsHelper
import com.adham.weatherSample.helpers.DimensionsHelper
import com.adham.weatherSample.presentation.viewModels.PlacesViewModel
import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.tasks.await
import org.koin.androidx.compose.koinViewModel

// I added a suppression temporarily in order to focus on implementing the unit tests properly.
@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerDialog(
    showSheet: Boolean,
    onDismiss: () -> Unit,
    onLocationSelected: (GeoByNameModel) -> Unit,
    viewModel: PlacesViewModel = koinViewModel(),
) {
    if (!showSheet) return

    val context = LocalContext.current

    val contextLang = LocalAppContext.current

    val fusedLocationClient =
        remember {
            LocationServices.getFusedLocationProviderClient(context)
        }

    val cameraPositionState = rememberCameraPositionState()
    var selectedLocation by remember { mutableStateOf<GeoByNameModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Get current location
    LaunchedEffect(Unit) {
        try {
            val location =
                fusedLocationClient
                    .getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        null,
                    ).await()

            location?.let {
                selectedLocation = GeoByNameModel(name = "", lon = it.longitude, lat = it.latitude)
            }
        } catch (_: SecurityException) {
        } finally {
            isLoading = false
        }
    }

    LaunchedEffect(selectedLocation) {
        selectedLocation?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.lat, it.lon),
                    AppConstantsHelper.DEFAULT_MAP_ZOOM,
                ),
                AppConstantsHelper.CAMERA_ANIMATION_DURATION,
            )
//            cameraPositionState.move(
//                CameraUpdateFactory.newLatLngZoom(current, AppConstantsHelper.DEFAULT_MAP_ZOOM),
//            )
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties =
            DialogProperties(
                usePlatformDefaultWidth = false,
            ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = Color.Transparent,
            tonalElevation = 6.dp,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .imePadding(),
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(DimensionsHelper.MAP_DIALOG_HEIGHT_TAG)
                        .padding(8.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    PlaceSearchField(
                        onAddressSelected = {
                            selectedLocation = it
                        },
                    )

                    Box(
                        Modifier
                            .weight(1.0f)
                            .fillMaxSize(),
                    ) {
                        GoogleMap(
                            modifier =
                                Modifier
                                    .matchParentSize()
                                    .pointerInteropFilter { false },
                            cameraPositionState = cameraPositionState,
                            uiSettings =
                                MapUiSettings(
                                    zoomControlsEnabled = true,
                                    zoomGesturesEnabled = true,
                                    scrollGesturesEnabled = true,
                                    tiltGesturesEnabled = true,
                                    rotationGesturesEnabled = true,
                                    myLocationButtonEnabled = true,
                                    scrollGesturesEnabledDuringRotateOrZoom = true,
                                ),
                            properties =
                                MapProperties(
                                    isMyLocationEnabled = true,
                                ),
                            onMapClick = { latLng ->
                                selectedLocation =
                                    GeoByNameModel(
                                        name = "",
                                        lon = latLng.longitude,
                                        lat = latLng.latitude,
                                    )
                            },
                        ) {
                            selectedLocation?.let {
                                Marker(
                                    state = MarkerState(position = LatLng(it.lat, it.lon)),
                                )
                            }
                        }
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    }
                    Button(
                        onClick = {
                            selectedLocation?.let {
                                onLocationSelected(it)
                                viewModel.clearData()
                                onDismiss()
                            }
                        },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                    ) {
                        Text(contextLang.getString(R.string.ConfirmLocation))
                    }
                }
            }
        }
    }
}
