import org.gradle.testing.jacoco.tasks.JacocoReport

val fileFilter = project.rootProject.extra["jacocoFileFilter"] as Set<String>

extensions.getByType<JacocoPluginExtension>().apply {
    toolVersion = "0.8.12"
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    group = "Reporting"
    description = "Generate Jacoco coverage reports for the debug build."

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val debugTree = fileTree("${layout.buildDirectory.get().asFile}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }
    val mainSrcJava = "${project.projectDir}/src/main/java"
    val mainSrcKotlin = "${project.projectDir}/src/main/kotlin"

    sourceDirectories.setFrom(files(mainSrcJava, mainSrcKotlin))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(layout.buildDirectory.get().asFile) {
        include("jacoco/testDebugUnitTest.exec", "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
        include("outputs/code_coverage/debugAndroidTest/connected/*coverage.ec")
    })
}
