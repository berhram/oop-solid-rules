package com.velvet.rules

import com.pinterest.ktlint.test.LintViolation
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
            """.trimIndent(),
            LintViolation(line = 3, col = 1, detail = "Do not use enum, sealed or open class")
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
            """.trimIndent(),
            LintViolation(line = 3, col = 1, detail = "Do not use enum, sealed or open class")
        )
    }

    @Test
    fun `invalid when open`() {
        assertLintErrors(
            """
                package com.velvet.rules

                open class Person
            """.trimIndent(),
            LintViolation(line = 3, col = 1, detail = "Do not use enum, sealed or open class")
        )
    }

    @Test
    fun valid() {
        assertNoLintErrors(
            """
                package com.velvet.rules

                class Person
            """.trimIndent()
        )
    }
}