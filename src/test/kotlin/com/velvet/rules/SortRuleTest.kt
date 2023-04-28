package com.velvet.rules

import com.pinterest.ktlint.test.LintViolation
import org.junit.jupiter.api.Test

class SortRuleTest : BaseTest(SortRule()) {

    @Test
    fun valid() {
        assertNoLintErrors(
            """
                class Person(val id: String) {
                
                    init { }
                
                    val prop = "prop"

                    constructor(robotId: Int) : this(id = "John${'$'}robotId")

                    fun hello() = Unit

                    companion object {

                        val propConst = "prop"
                    }
                }
            """.trimIndent()
        )
    }

    @Test
    fun `invalid when not sorted`() {
        assertLintErrors(
            """
                class Person(val id: String) {
                
                    companion object {

                        val propConst = "prop"
                    }

                    fun hello() = Unit

                    init { }

                    constructor(robotId: Int) : this(id = "John${'$'}robotId")

                    val prop = "prop"
                }
            """.trimIndent(),
            LintViolation(line = 8, col = 5, detail = "The fun hello is not in the expected order"),
            LintViolation(line = 12, col = 5, detail = "The constructor is not in the expected order"),
            LintViolation(line = 14, col = 5, detail = "The class element prop is not in the expected order")
        )
    }

    @Test
    fun `invalid when property after constructor`() {
        assertLintErrors(
            """
            class Person(val id: String) {
                constructor(robotId: Int) : this(id = "John${'$'}robotId")
                private class SomeClass1
                val prop = "prop"
                private class SomeClass2
            }
        """.trimIndent(),
            LintViolation(line = 4, col = 5, detail = "The class element prop is not in the expected order")
        )
    }

    @Test
    fun `invalid when property after method`() {
        assertLintErrors(
            """
            class Person(val id: String) {
                fun hello() = Unit
                val prop = "prop"
            }
        """.trimIndent(),
            LintViolation(line = 3, col = 5, detail = "The class element prop is not in the expected order")
        )
    }

    @Test
    fun `invalid when property after companion object`() {
        assertLintErrors(
            """
            class Person(val id: String) {
                companion object {
                    val propConst = "prop"
                }
                val prop = "prop"
            }
        """.trimIndent(),
            LintViolation(line = 5, col = 5, detail = "The class element prop is not in the expected order")
        )
    }

    @Test
    fun `invalid when constructor after method`() {
        assertLintErrors(
            """
            class Person(val id: String) {
                fun hello() = Unit
                constructor(robotId: Int) : this(id = "John${'$'}robotId")
            }
        """.trimIndent(), LintViolation(line = 3, col = 5, detail = "The constructor is not in the expected order")
        )
    }

    @Test
    fun `invalid when constructor after companion object`() {
        assertLintErrors(
            """
            class Person(val id: String) {
                companion object {
                    val propConst = "prop"
                }
                constructor(robotId: Int) : this(id = "John${'$'}robotId")
            }
        """.trimIndent(), LintViolation(line = 5, col = 5, detail = "The constructor is not in the expected order")
        )
    }

    @Test
    fun `invalid when method after companion object`() {
        assertLintErrors(
            """
            class Person(val id: String) {
                companion object {
                    val propConst = "prop"
                }
                fun hello() = Unit
            }
        """.trimIndent(), LintViolation(line = 5, col = 5, detail = "The fun hello is not in the expected order")
        )
    }
}