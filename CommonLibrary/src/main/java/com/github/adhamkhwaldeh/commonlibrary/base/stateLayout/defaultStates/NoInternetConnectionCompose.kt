package com.github.adhamkhwaldeh.commonlibrary.base.stateLayout.defaultStates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.adhamkhwaldeh.commonlibrary.R

/**
 * No internet connection compose
 *
 * @param retry
 * @receiver
 */
@Composable
fun NoInternetConnectionCompose(
    retry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth(),
//            .fillMaxHeight()
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.noConnection),
            style = MaterialTheme.typography.labelLarge,
        )
        OutlinedButton(
            modifier = Modifier.padding(8.dp),
            onClick = {
                retry()
            },
        ) {
            Text(text = stringResource(R.string.Retry))
        }
    }
}
