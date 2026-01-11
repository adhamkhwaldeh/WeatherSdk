package com.adham.weatherSdk

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.junit.ArchUnitRunner
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import org.junit.runner.RunWith

@RunWith(ArchUnitRunner::class)
@AnalyzeClasses(packages = ["com.adham.weatherSdk"])
class ArchUnitTest {

    @ArchTest
    @JvmField
    val `no cyclic dependencies should exist`: ArchRule =
        slices()
            .matching("com.adham.weatherSdk.(*)..")
            .should()
            .beFreeOfCycles()

    @ArchTest
    @JvmField
    val `domain layer should not depend on data or networking`: ArchRule =
        classes()
            .that().resideInAPackage("..domain..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..domain..", "java..", "kotlin..", "kotlinx.coroutines..", "com.github.adhamkhwaldeh.commonlibrary..", "com.adham.weatherSdk.data.states..")

    @ArchTest
    @JvmField
    val `repository interfaces should be in domain`: ArchRule =
        classes()
            .that().haveSimpleNameEndingWith("Repository")
            .and().areInterfaces()
            .should().resideInAPackage("..domain.repositories..")

    @ArchTest
    @JvmField
    val `repository implementations should be in data`: ArchRule =
        classes()
            .that().haveSimpleNameEndingWith("RepositoryImpl")
            .should().resideInAPackage("..data.repositories..")

    @ArchTest
    @JvmField
    val `use cases should be in domain and end with UseCase`: ArchRule =
        classes()
            .that().resideInAPackage("..domain.useCases..")
            .should().haveSimpleNameEndingWith("UseCase")

    @ArchTest
    @JvmField
    val `networking should not be accessed by domain`: ArchRule =
        classes()
            .that().resideInAPackage("..networking..")
            .should().onlyBeAccessed().byClassesThat()
            .resideInAnyPackage("..data.repositories..", "..providers..", "..networking..", "..domain.useCases..")

    @ArchTest
    @JvmField
    val `dtos should reside in data dtos package`: ArchRule =
        classes()
            .that().haveSimpleNameEndingWith("Response")
            .should().resideInAPackage("..data.dtos..")

    @ArchTest
    @JvmField
    val `all ViewModels should end with ViewModel`: ArchRule =
        classes()
            .that()
            .resideInAPackage("..viewModels..")
            .should()
            .haveSimpleNameEndingWith("ViewModel")
}
