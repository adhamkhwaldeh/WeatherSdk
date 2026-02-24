import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

// import androidx.compose.ui.graphics.setFrom
// import androidx.glance.appwidget.compose

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Recommended: Use the alias from libs.versions.toml
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
//    id 'com.android.application' version '8.13.2' apply false
//    id 'com.android.library' version '8.13.2' apply false
//    id 'org.jetbrains.kotlin.android' version '2.3.0' apply false
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.dokka)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.compose)
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    ktlint {
        android = true
        ignoreFailures = false
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
        }
    }
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        android.set(true)
        ignoreFailures.set(false)
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
        }
    }

//    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
//        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
//        baseline.set(file("$rootDir/config/detekt/baseline.xml"))
//        buildUponDefaultConfig.set(true)
//        allRules.set(false)
//        autoCorrect.set(true)
//
//        reports {
//            html.required.set(true)
//            xml.required.set(true)
//            txt.required.set(false)
//        }
//    }

    detekt {
        // Points to a config file we will create in step 3
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))

        baseline = file("$rootDir/config/detekt/baseline.xml")

        // Builds a report for easier reading
        buildUponDefaultConfig = true
        allRules = false
        autoCorrect = true

        reports {
            html.required.set(true) // Readable in browser
            xml.required.set(true) // For CI/CD tools
            txt.required.set(false)
        }
    }
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
        includes.from("WeatherSDK/IntegrationGuide.md")
        skipEmptyPackages.set(true)
//        includeNonPublic.set(false)
//        includes.from("IntegrationGuide.md")
        sourceLink {
//            localDirectory.set(file("src/main/java"))
            remoteUrl("https://github.com/adhamkhwaldeh/WeatherSdk/tree/main/app/src/main/java")
            remoteLineSuffix.set("#L")
        }
    }

    dokkaSourceSets.configureEach {

        includes.from("IntegrationGuide.md") // Include custom documentation
        reportUndocumented.set(true) // Warn about undocumented public APIs
        skipDeprecated.set(true) // Exclude deprecated elements
        suppress.set(false) // Include suppressed elements
        sourceRoots.from(file("src/main/java"))
        sourceRoots.from("src/main/java")
        jdkVersion.set(17)
    }
//    pluginsConfiguration.html {
//        customStyleSheets.from("styles.css")
//        customAssets.from("logo.png")
//        footerMessage.set("(c) Your Company")
//    }

//    // Dokka generates a new process managed by Gradle
//    dokkaGeneratorIsolation = ProcessIsolation {
//        // Configures heap size
//        maxHeapSize = "4g"
//    }
//    // Runs Dokka in the current Gradle process
//    dokkaGeneratorIsolation = ClassLoaderIsolation()
}
