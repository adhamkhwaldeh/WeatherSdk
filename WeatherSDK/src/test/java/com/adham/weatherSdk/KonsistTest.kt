package com.adham.weatherSample

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withNameMatching
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class KonsistTest {
    @Test
    fun `all ViewModels should end with ViewModel`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameMatching(".*ViewModel")
            .assert()
    }

    @Test
    fun `all repository interfaces should be in the data package`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withNameEndingWith("Repository")
            .assertTrue { it.resideInPackage("..data..") }
    }

    @Test
    fun `all UseCases should be in domain package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .resideInPackage("com.example.myapp.domain..")
            .assert()
    }

    @Test
    fun `all classes should have a KDoc comment`() {
        Konsist
            .scopeFromProject()
            .classes()
            .assert { it.hasKDoc() }
    }

    @Test
    fun `file names should match class names`() {
        Konsist
            .scopeFromProject()
            .files()
            .assert { it.nameWithoutExtension == it.declarations().firstOrNull()?.name }
    }
}
