package com.adham.weatherSample

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.junit.jupiter.api.Test

@AnalyzeClasses(packages = ["com.adham.weathersdk"])
class ArchUnitTest {
    @Test
    fun `controllers should only depend on services`() {
        val rule =
            ArchRuleDefinition
                .classes()
                .that()
                .resideInAPackage("..controller..")
                .should()
                .onlyAccessClassesThat()
                .resideInAnyPackage("..service..", "..util..")

        rule.check(JavaClasses.importFrom(ClassLoader.getSystemClassLoader()))
    }

    @Test
    fun `no cyclic dependencies should exist`() {
        val rule =
            ArchRuleDefinition
                .noClasses()
                .should()
                .dependOnEachOther()

        rule.check(JavaClasses.importFrom(ClassLoader.getSystemClassLoader()))
    }

    @Test
    fun `all ViewModels should end with ViewModel`() {
        val rule =
            ArchRuleDefinition
                .classes()
                .that()
                .resideInAPackage("..viewmodel..")
                .should()
                .haveSimpleNameEndingWith("ViewModel")

        rule.check(JavaClasses.importFrom(ClassLoader.getSystemClassLoader()))
    }
}
