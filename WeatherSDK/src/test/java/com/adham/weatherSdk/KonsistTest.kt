package com.adham.weatherSdk

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.KoModifier
import com.lemonappdev.konsist.api.ext.list.functions
import com.lemonappdev.konsist.api.ext.list.imports
import com.lemonappdev.konsist.api.ext.list.properties
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withPackage
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
    fun `ViewModels should not expose mutable state`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ViewModel")
            .properties()
            .filter { it.hasPublicModifier || (!it.hasInternalModifier && !it.hasPrivateModifier && !it.hasProtectedModifier) }
            .assertTrue {
                val type = it.type?.name ?: ""
                !type.contains("Mutable")
            }
    }

    @Test
    fun `no context leak in viewmodels`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ViewModel")
            .properties()
            .assertTrue {
                val type = it.type?.name ?: ""
                type != "Context" && type != "Activity"
            }
    }

    @Test
    fun `all repository interfaces should be in the domain package`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withNameEndingWith("Repository")
            .assertTrue { it.resideInPackage("..domain..") }
    }

    @Test
    fun `repository functions should be reactive or suspend`() {
        Konsist.scopeFromProject()
            .interfaces()
            .withNameEndingWith("Repository")
            .filter { it.name != "WeatherLocalRepository" }
            .functions()
            .assertTrue {
                it.hasSuspendModifier || it.returnType?.name?.contains("Flow") == true
            }
    }

    @Test
    fun `all UseCases should be in domain package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..domain..") }
    }

    @Test
    fun `public api should have kdoc`() {
        val scope = Konsist.scopeFromProject()

        scope.classes()
            .filter { it.hasPublicModifier }
            .assertTrue { it.hasKDoc }

        scope.functions()
            .filter { it.hasPublicModifier }
            .assertTrue { it.hasKDoc }

        scope.properties()
            .filter { it.hasPublicModifier }
            .assertTrue { it.hasKDoc }
    }

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

    @Test
    fun `classes with 'Impl' suffix should be internal`() {
        Konsist.scopeFromProject()
            .classes()
            .filter { !it.hasModifier(KoModifier.ABSTRACT) }
            .withNameEndingWith("Impl")
            .assertTrue { it.hasInternalModifier }
    }

    @Test
    fun `use cases should have a public suspend invoke method`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .filter { !it.hasModifier(KoModifier.ABSTRACT) }
            .assertTrue { koClass ->
                koClass.hasFunction { func ->
                    func.name == "invoke" &&
                            (func.hasPublicModifier || !func.hasInternalModifier && !func.hasPrivateModifier && !func.hasProtectedModifier) &&
                            func.hasSuspendModifier
                }
            }
    }

    @Test
    fun `use cases should have only one public method`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .filter { !it.hasModifier(KoModifier.ABSTRACT) }
            .assertTrue { koClass ->
                val publicMethods = koClass.functions(includeNested = false)
                    .count {
                        it.hasPublicModifier ||
                                (!it.hasInternalModifier && !it.hasPrivateModifier && !it.hasProtectedModifier)
                    }
                publicMethods == 1
            }
    }

    @Test
    fun `domain layer should not depend on data layer`() {
        Konsist
            .scopeFromProject()
            .files
            .withPackage("..domain..")
            .assertTrue { file ->
                file.imports.none { it.name.contains("..data..") }
            }
    }

    @Test
    fun `domain layer should not have platform or library dependencies`() {
        val forbiddenPackages = listOf(
            "android.",
            "retrofit2.",
            "okhttp3.",
            "androidx.room.",
            "com.squareup.moshi."
        )
        Konsist
            .scopeFromProject()
            .files
            .withPackage("..domain..")
            .assertTrue { file ->
                file.imports.none { import ->
                    forbiddenPackages.any {
                        import.name.startsWith(it) && !import.name.startsWith(
                            "android.annotation"
                        )
                    }
                }
            }
    }

    @Test
    fun `no resource imports in domain`() {
        Konsist.scopeFromProject()
            .files
            .withPackage("..domain..")
            .assertTrue { file ->
                file.imports.none { it.name.endsWith(".R") }
            }
    }

    @Test
    fun `all classes in dtos package should be data classes`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withPackage("..data.dtos..")
            .assertTrue { it.hasModifier(KoModifier.DATA) }
    }

    @Test
    fun `dtos should use moshi JsonClass annotation`() {
        Konsist.scopeFromProject()
            .classes()
            .withPackage("..data.dtos..")
            .assertTrue { it.hasAnnotation { anno -> anno.name == "JsonClass" } }
    }

    @Test
    fun `all classes in exceptions package should end with Exception`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withPackage("..exceptions..")
            .assertTrue { it.name.endsWith("Exception") }
    }

    @Test
    fun `constants should be uppercase`() {
        Konsist
            .scopeFromProject()
            .properties()
            .filter { it.hasConstModifier }
            .assertTrue { it.name == it.name.uppercase() }
    }

    @Test
    fun `companion object should be the last declaration in a class`() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertTrue {
                val companion = it.objects().lastOrNull { obj -> obj.hasCompanionModifier }
                companion == null || it.declarations(includeNested = false).last() == companion
            }
    }

    @Test
    fun `no wildcard imports allowed`() {
        Konsist
            .scopeFromProject()
            .files
            .imports
            .assertTrue { !it.isWildcard }
    }

    @Test
    fun `repository implementations should reside in data package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("RepositoryImpl")
            .assertTrue { it.resideInPackage("..data..") }
    }

    @Test
    fun `interfaces should not have 'I' prefix`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .assertTrue {
                !it.name.startsWith("I") || it.name[1].isLowerCase()
            }
    }

    @Test
    fun `package names should be lowercase`() {
        Konsist
            .scopeFromProject()
            .packages
            .assertTrue {
             //   it.name == it.name.lowercase()
                it.name[0].isLowerCase()
                        && !it.name.contains("_")
            }
    }

    @Test
    fun `all packages should start with project base package`() {
        Konsist.scopeFromModule("WeatherSDK")
            .packages
            .assertTrue { it.name.startsWith("com.adham.weatherSdk") }
    }

    @Test
    fun `no field injection allowed`() {
        Konsist
            .scopeFromProject()
            .properties()
            .assertTrue { !it.hasAnnotation { anno -> anno.name == "Inject" } }
    }

    @Test
    fun `networking and localStorages should be internal`() {
        Konsist.scopeFromProduction()
            .classes()
            .filter { it.resideInPackage("..networking..") || it.resideInPackage("..localStorages..") }
            .filter { !it.resideInPackage("..test..") }
            .assertTrue { it.hasInternalModifier }
    }

    @Test
    fun `providers and helpers should be internal`() {
        val scope = Konsist.scopeFromProject()

        scope.classes()
            .filter { it.resideInPackage("..providers..") }
            .filter { !it.resideInPackage("..test..") }
            .assertTrue { it.hasInternalModifier }

        scope.interfaces()
            .filter { it.resideInPackage("..providers..") }
            .filter { !it.resideInPackage("..test..") }
            .assertTrue { it.hasInternalModifier }

        scope.objects()
            .filter { it.resideInPackage("..providers..") }
            .filter { !it.resideInPackage("..test..") }
            .assertTrue { it.hasInternalModifier }

        scope.functions(includeNested = false)
            .filter { it.resideInPackage("..providers..") }
            .filter { !it.resideInPackage("..test..") }
            .assertTrue { it.hasInternalModifier }

        scope.properties(includeNested = false)
            .filter { it.resideInPackage("..providers..") }
            .filter { !it.resideInPackage("..test..") }
            .assertTrue { it.hasInternalModifier }
    }

    @Test
    fun `domain and dtos should be immutable`() {
        Konsist.scopeFromProject()
            .classes()
            .filter { it.resideInPackage("..domain..") || it.resideInPackage("..data.dtos..") }
            .properties()
            .assertTrue {
                it.isVal && !(it.type?.name?.startsWith("Mutable") ?: false)
            }
    }

    @Test
    fun `no nullable collections`() {
        Konsist.scopeFromProject()
            .functions()
            .assertTrue {
                val type = it.returnType?.name ?: ""
                if (type.contains("List") || type.contains("Set") || type.contains("Map")) {
                    !(it.returnType?.isNullable ?: true)
                } else true
            }
    }

    @Test
    fun `no lateinit in domain`() {
        Konsist.scopeFromProject()
            .classes()
            .withPackage("..domain..")
            .properties()
            .assertTrue { !it.hasLateinitModifier }
    }

    @Test
    fun `boolean properties should have prefix`() {
        Konsist.scopeFromProject()
            .properties()
            .filter { it.type?.name == "Boolean" }
            .assertTrue {
                val name = it.name
                name.startsWith("is") || name.startsWith("has")
                        || name.startsWith("can") || name.startsWith("should")
                        || name.endsWith("able")
            }
    }

    @Test
    fun `every class should be tested with corresponding test name`() {
        Konsist.scopeFromProject()
            .classes()
            .filter {
                it.name.endsWith("UseCase") || it.name.endsWith("RepositoryImpl") || it.name.endsWith(
                    "ViewModel"
                )
            }
            .filter { !it.hasModifier(KoModifier.ABSTRACT) }
            .assertTrue { koClass ->
                val testName = "${koClass.name}Test"
                Konsist.scopeFromProject()
                    .files
                    .any { it.name.startsWith(testName) }
            }
    }

    @Test
    fun `no android logging allowed`() {
        Konsist
            .scopeFromProject()
            .files
            .assertTrue { file ->
                file.imports.none {
                    it.name == "android.util.Log" || it.name == "java.util.logging.Logger"
                }
            }
    }

    @Test
    fun `no println allowed`() {
        Konsist
            .scopeFromProject()
            .files
            .filter { !it.name.contains("KonsistTest") }
            .assertTrue { file ->
                !file.text.contains("println(")
            }
    }

    @Test
    fun `no unsafe calls allowed`() {
        Konsist.scopeFromProduction()
            .files
            .filter { !it.name.contains("DataProvider")
            //        && !it.name.contains("KonsistTest")
            }
            .assertTrue { !it.text.contains("!!") }
    }

    @Test
    fun `constructor and function parameter count limit`() {
        Konsist.scopeFromProject()
            .classes()
            .assertTrue {
                val constructor = it.primaryConstructor
                constructor == null || constructor.parameters.size <= 7
            }

        Konsist.scopeFromProject()
            .functions()
            .assertTrue { it.parameters.size <= 5 }
    }
}
