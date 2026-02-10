plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
//    alias(libs.plugins.kotlin.jvm)

    id("maven-publish")
}

android {
    namespace = "com.adham.weatherSdk"
    compileSdk = 36

    defaultConfig {
        minSdk = 30
//        targetSdk = 36
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

//    defaultConfig {
//        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
//    }
//
//    testOptions {
//        managedDevices {
//            devices {
//                pixel2Api30(com.android.build.api.dsl.ManagedVirtualDevice::class) {
//                    device = "Pixel 2"
//                    apiLevel = 30
//                    systemImageSource = "aosp"
//                }
//            }
//        }
//    }
//
    testOptions {
//        unitTests.includeAndroidResources = true
//        unitTests.all {
//            useJUnitPlatform()
//        }
    }
    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    buildTypes {
        getByName("release") {
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
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "mockito-extensions/org.mockito.plugins.MemberAccessor"
            excludes += "mockito-extensions/org.mockito.plugins.MockMaker"
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.adhamkhwaldeh.WeatherSdk"
                artifactId = "WeatherSDK"
                version = "1.0"
            }
        }
    }
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
    //    // Optional - Integration with LiveData
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
    // Use the BOM to align versions (fixes the OutputDirectoryProvider error)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.jupiter.junit.jupiter)

    // Maintain JUnit 4 support if you have older tests
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

// Koin dependencies
//    androidTestImplementation("io.insert-koin:koin-test:$koinVersion") // Koin testing tools
//            {
//                exclude group: 'org.mockito'
//            }
    testImplementation(libs.koin.test) // Koin testing tools
    debugImplementation(libs.koin.test) // Koin testing tools

    androidTestImplementation(libs.core.testing)
    androidTestImplementation(libs.core)
    testImplementation(libs.core)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.testing)

    androidTestImplementation(libs.androidx.runner)
    androidTestUtil(libs.androidx.orchestrator)

    testImplementation(libs.hamcrest.all)
    testImplementation(libs.mockito.core)

    // Having the actual dependency helps, instead of removing it by accident
    // when adding the fix originally present, which is also not needed anymore.

//    testImplementation 'org.mockito.kotlin:mockito-kotlin:5.4.0'
//    androidTestImplementation 'org.mockito.kotlin:mockito-kotlin:5.4.0'

    implementation(libs.mockk.mockk.android)
//    implementation "io.mockk:mockk:1.13.5"
//    implementation "org.mockito:mockito-core:5.15.2" // For pure unit tests
//    implementation "org.mockito:mockito-inline:5.2.0" // For final classes or static methods
//    implementation "org.mockito:mockito-android:5.15.2"
//    implementation("org.mockito:mockito-android:")

//    testImplementation "io.mockk:mockk:1.13.5"
//    testImplementation "org.mockito:mockito-core:5.15.2" // For pure unit tests
//    testImplementation "org.mockito:mockito-inline:5.2.0" // For final classes or static methods
//    testImplementation "org.mockito:mockito-android:5.5.0"
//    testImplementation "org.mockito:mockito-android:5.15.2"

    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.mockk.android)
//    androidTestImplementation "io.mockk:mockk:1.13.5"
//    androidTestImplementation "org.mockito:mockito-core:5.15.2" // For pure unit tests
//    androidTestImplementation "org.mockito:mockito-inline:5.2.0"
    // For final classes or static methods
//    androidTestImplementation "org.mockito:mockito-android:5.5.0"
//    androidTestImplementation "org.mockito:mockito-android:5.15.2"
//    androidTestImplementation 'com.squareup.retrofit2:retrofit-mock:2.11.0'

//    testImplementation 'com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-RC3'
//    testImplementation ("org.mockito.kotlin:mockito-kotlin:$latest_version")

    // For Android instrumentation tests

    // Robolectric: For UI-related ViewModel tests (e.g., testing interactions with UI elements).
    // Truth: A fluent testing library, often used with Google libraries.
    testImplementation(libs.truth)

    testImplementation(libs.paparazzi)
    testImplementation(libs.androidx.compose.ui.ui.test.junit4)
    // https://mvnrepository.com/artifact/com.squareup/kotlinpoet
    runtimeOnly(libs.kotlinpoet)

//    implementation(libs.androidx.benchmark.macro)
//    androidTestImplementation(libs.androidx.benchmark.macro)
//    androidTestImplementation(libs.androidx.benchmark.junit4)
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit4)

    // archunit
    testImplementation(libs.archunit)
    testImplementation(libs.archunit.junit4)
    testImplementation(libs.archunit.junit5) // If using JUnit 5

    // konsist
    testImplementation(libs.konsist) // Use the latest version

    //endregion

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
        includes.from("IntegrationGuide.md")
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
