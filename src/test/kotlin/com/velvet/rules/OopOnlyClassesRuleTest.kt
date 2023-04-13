package com.velvet.rules

import org.junit.jupiter.api.Test


class OopOnlyClassesRuleTest : BaseTest(OopOnlyClassesRule()) {

    @Test
    fun `invalid when sealed`() {
        assertLintErrors(
            """
                sealed class Person {
    
                    object John : Person()
                }
            """
        )
    }

    @Test
    fun `invalid when enum`() {
        assertLintErrors(
            """
                enum class Person {
    
                    JOHN, NICK
                }
            """
        )
    }

    @Test
    fun `invalid when open`() {
        assertLintErrors(
            """
                open class Person
            """
        )
    }

    @Test
    fun `valid`() {
        assertNoLintErrors(
            """
                class Person
            """
        )
    }
}