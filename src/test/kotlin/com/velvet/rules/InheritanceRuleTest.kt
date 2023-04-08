package com.velvet.rules

import org.junit.jupiter.api.Test

class InheritanceRuleTest : BaseTest(InheritanceRule()) {

    @Test
    fun `passed when abstract class`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                abstract class SomeClass

                interface SomeInterface

                interface SomeInterface2

                class Repository : SomeClass(), SomeInterface, SomeInterface2
            """
        )
    }

    @Test
    fun `passed when interface`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                interface SomeClass

                class Repository : SomeClass
            """
        )
    }

    @Test
    fun `passed when open class`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                open class SomeClass

                class Repository : SomeClass()
            """
        )
    }

    @Test
    fun `no passed`() {
        assertLintErrors(
            """
                package com.github.johnnysc.practicetdd

                class Repository
            """
        )
    }
}