package com.adham.weatherSample.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import com.adham.weatherSample.LocalAppContext
import com.adham.weatherSample.R
import com.adham.weatherSample.helpers.TestingConstantHelper
import com.adham.weatherSample.presentation.viewModels.PlacesViewModel
import com.adham.weatherSdk.domain.models.GeoByNameModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceSearchField(
    onAddressSelected: (GeoByNameModel) -> Unit,
    viewModel: PlacesViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalAppContext.current

    LaunchedEffect(uiState.selectedPlace) {
        uiState.selectedPlace?.apply {
            onAddressSelected(this)
        }
    }

    Column {
        ExposedDropdownMenuBox(
            expanded = uiState.isExpanded && uiState.results.isNotEmpty(),
            onExpandedChange = {
            },
        ) {
            OutlinedTextField(
                value = uiState.query,
                onValueChange = viewModel::onQueryChange,
                label = { Text(text = context.getString(R.string.EnterYourCityName)) },
                placeholder = { Text(text = context.getString(R.string.CityName)) },
                singleLine = true,
                trailingIcon = {
                    if (uiState.query.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.clearData()
                        }) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                                contentDescription = TestingConstantHelper.CLEAR_TEXT,
                                tint = Color.Gray,
                            )
                        }
                    }
                },
                modifier =
                    Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .testTag(TestingConstantHelper.CITY_INPUT_TAG),
            )

            ExposedDropdownMenu(
                expanded = uiState.isExpanded && uiState.results.isNotEmpty(),
                onDismissRequest = {
                    viewModel.updateState { it.copy(isExpanded = false) }
                },
            ) {
                uiState.results.forEach { place ->
                    DropdownMenuItem(
                        text = { Text(place.name) },
                        onClick = {
                            viewModel.loadPlaceDetails(placeId = place.placeId)
                            viewModel.updateState { it.copy(isExpanded = false) }
                        },
                    )
                }
            }
        }
    }
}
