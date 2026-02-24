plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dokka)
    id("kotlin-kapt")
}

android {
    namespace = "com.adham.weatherSdk"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
//        unitTests.isReturnDefaultValues = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//            excludes += "META-INF/NOTICE.md"
//            excludes += "META-INF/LICENSE.md"
//            excludes += "META-INF/LICENSE-notice.md"
//            excludes += "mockito-extensions/org.mockito.plugins.MemberAccessor"
//            excludes += "mockito-extensions/org.mockito.plugins.MockMaker"
//        }
//    }
}

dependencies {

    //region Support Packages
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(platform(libs.kotlin.bom))
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.compose.runtime.livedata)
    testImplementation(libs.junit.jupiter)
    //endregion

    implementation(libs.androidx.security.crypto)

    //region Retrofit
    // Moshi
    implementation(libs.moshi.kotlin)

    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.logging.interceptor) //
    //endregion

    //region Testing package
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.jupiter.junit.jupiter)
    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.vintage.engine)

    testImplementation(libs.junit)
    testImplementation(libs.okhttp3.mockwebserver)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.robolectric)

    testImplementation(libs.koin.test)
    debugImplementation(libs.koin.test)

    androidTestImplementation(libs.core.testing)
    androidTestImplementation(libs.core)
    testImplementation(libs.core)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.testing)

    androidTestImplementation(libs.androidx.runner)
    androidTestUtil(libs.androidx.orchestrator)

    testImplementation(libs.hamcrest.all)
    testImplementation(libs.mockito.core)

    implementation(libs.mockk.mockk.android)

    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.mockk.android)

    testImplementation(libs.truth)

    testImplementation(libs.paparazzi)
    testImplementation(libs.androidx.compose.ui.ui.test.junit4)
    runtimeOnly(libs.kotlinpoet)
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit4)

    // archunit
    testImplementation(libs.archunit)
    testImplementation(libs.archunit.junit4)
    testImplementation(libs.archunit.junit5)

    // konsist
    testImplementation(libs.konsist)

    //endregion

    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)
    api(libs.androidx.room.paging)
    implementation(libs.androidx.security.crypto)
    kapt(libs.androidx.room.compiler)
    testImplementation(libs.androidx.room.testing)

    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
//    kapt(libs.moshi.kotlin.codegen)

    implementation(libs.places)

    api(project(":CommonLibrary"))
    api(project(":CommonSDK"))
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

dokka {
    moduleName.set("WeatherSDK")
    dokkaPublications.html {
        outputDirectory.set(layout.buildDirectory.dir("dokkaDir"))
    }

    dokkaSourceSets.main {
        // Only set the actual Kotlin/Java dirs once
        sourceRoots.from("src/main/java")
//    dokkaSourceSets.named("main") {
        includes.from("README.md")
        skipEmptyPackages.set(true)
//        includeNonPublic.set(false)
//        includes.from("IntegrationGuide.md")
        sourceLink {
//            localDirectory.set(file("src/main/java"))
            remoteUrl("https://github.com/adhamkhwaldeh/WeatherSdk/tree/main/app/src/main/java")
            remoteLineSuffix.set("#L")
        }

        reportUndocumented.set(true) // Warn about undocumented public APIs
        skipDeprecated.set(true) // Exclude deprecated elements
        suppress.set(false) // Include suppressed elements
        sourceRoots.from(file("src/main/java"))
        sourceRoots.from("src/main/java")
        jdkVersion.set(17)
    }
}
