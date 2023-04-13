package com.velvet.rules

import org.junit.jupiter.api.Test


class OopOnlyClassesRuleTest : BaseTest(OopOnlyClassesRule()) {

    @Test
    fun `invalid when sealed`() {
        assertLintErrors(
            """
                package com.velvet.rules

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
                package com.velvet.rules

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
                package com.velvet.rules

                open class Person
            """
        )
    }

    @Test
    fun valid() {
        assertNoLintErrors(
            """
                package com.velvet.rules

                class Person
            """
        )
    }
}