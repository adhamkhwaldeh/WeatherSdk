package com.adham.weatherSdk

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices

@AnalyzeClasses(packages = ["com.adham.weatherSdk"])
class ArchUnitTest {

    @ArchTest
    val `controllers should only depend on services`: ArchRule =
        classes()
            .that()
            .resideInAPackage("..controller..")
            .should()
            .onlyAccessClassesThat()
            .resideInAnyPackage("..service..", "..util..", "java..", "kotlin..", "com.adham.weatherSdk..")

    @ArchTest
    val `no cyclic dependencies should exist`: ArchRule =
        slices()
            .matching("com.adham.weatherSdk.(*)..")
            .should()
            .beFreeOfCycles()

    @ArchTest
    val `all ViewModels should end with ViewModel`: ArchRule =
        classes()
            .that()
            .resideInAPackage("..viewmodel..")
            .should()
            .haveSimpleNameEndingWith("ViewModel")
}
