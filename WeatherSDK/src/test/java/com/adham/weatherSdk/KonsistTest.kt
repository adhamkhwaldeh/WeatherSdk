package com.adham.weatherSdk

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withParentNamed
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class KonsistTest {
    @Test
    fun `all ViewModels should end with ViewModel`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withParentNamed("ViewModel")
            .assertTrue { it.name.endsWith("ViewModel") }
    }

    @Test
    fun `all repository interfaces should be in the data package`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withNameEndingWith("Repository")
            .assertTrue { it.resideInPackage("..repositories..") }
    }

    @Test
    fun `all UseCases should be in domain package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..domain..") }
    }

//    @Test
//    fun `all classes should have a KDoc comment`() {
//        Konsist
//            .scopeFromProject()
//            .classes()
//            .assertTrue { it.hasKDoc }
//    }

    @Test
    fun `file names should match class names`() {
        Konsist
            .scopeFromProject()
            .files
            .assertTrue { file ->
                val className = file.classes().firstOrNull()?.name
                className == null || file.name == className
            }
    }
}
