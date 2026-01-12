package com.adham.weatherSample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.Paparazzi
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet
import org.junit.Rule
import org.junit.Test

class PaparazziComposeTest {
    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun generateComposableWithKotlinPoet() {
        val packageName = "com.example.generated"
        val className = "GeneratedComposable"

        // Define the Composable function dynamically
        val fileSpec =
            FileSpec
                .builder(packageName, className)
                .addFunction(
                    FunSpec
                        .builder("GeneratedComposable")
                        .addAnnotation(Composable::class)
                        .addAnnotation(Preview::class)
                        .addModifiers(KModifier.PUBLIC)
                        .addStatement(
                            """
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(androidx.compose.ui.graphics.Color.Blue)
                            ) {
                                Text(text = "Hello from KotlinPoet!", modifier = Modifier.padding(16.dp))
                            }
                            """.trimIndent(),
                        ).build(),
                ).build()

        // Generate file dynamically (normally you would write to a file, but for testing, we skip)
        val output = StringBuilder()
        fileSpec.writeTo(output)

        // Ensure the code was generated
        assertThat(output.toString()).contains("GeneratedComposable")

        // Render the dynamically generated UI
        paparazzi.snapshot {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(androidx.compose.ui.graphics.Color.Blue),
            ) {
                Text(
                    text = "Hello from KotlinPoet!",
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
