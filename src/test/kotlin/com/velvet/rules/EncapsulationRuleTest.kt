package com.velvet.rules

import com.pinterest.ktlint.test.LintViolation
import org.junit.jupiter.api.Test

class EncapsulationRuleTest : BaseTest(EncapsulationRule()) {

    @Test
    fun `passed both private`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                class Repository(private val dataSource: DataSource) {      
                
                    private var page: Int = 0
                }
            """.trimIndent()
        )
    }

    @Test
    fun `passed because override`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                class Repository(override val dataSource: DataSource) {

                    override var page: Int = 0
                }
            """.trimIndent()
        )
    }

    @Test
    fun `passed because entity`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                @Entity(tableName = "user_table")
                class User(val a: String) {

                    val b: Boolean = false
                }
            """.trimIndent()
        )
    }

    @Test
    fun `passed one private one protected`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                abstract class Repository(private val dataSource: DataSource) {

                    protected var page: Int = 0
                }
            """.trimIndent()
        )
    }

    @Test
    fun `passed one protected one private`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                abstract class Repository(protected val dataSource: DataSource) {

                    private var page: Int = 0
                }
            """.trimIndent()
        )
    }

    @Test
    fun `passed both protected`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                abstract class Repository(protected val dataSource: DataSource) {

                    protected var page: Int = 0
                }
            """.trimIndent()
        )
    }

    @Test
    fun `not passed both public or internal`() {
        assertLintErrors(
            """
                package com.github.johnnysc.practicetdd
                
                class Repository(internal val dataSource: DataSource) {
                
                    var page: Int = 0
                }
            """.trimIndent(),
            LintViolation(line = 3, col = 18, detail = "The constructor argument dataSource must be private"),
            LintViolation(line = 5, col = 5, detail = "The page property must be private")
        )
    }

    @Test
    fun `abstract class not passed in constructor when one protected`() {
        assertLintErrors(
            """
                package com.github.johnnysc.practicetdd
                    
                abstract class Repository(internal val dataSource: DataSource) {
                
                   protected val page: Int
                }
            """.trimIndent(),
            LintViolation(line=3, col=27, detail="The constructor argument dataSource must be private or protected")
        )
    }

    @Test
    fun `abstract class not passed in constructor when one private`() {
        assertLintErrors(
            """
                package com.github.johnnysc.practicetdd
                    
                abstract class Repository(val dataSource: DataSource) {
                
                   private val page: Int
                }
            """.trimIndent(),
            LintViolation(line=3, col=27, detail="The constructor argument dataSource must be private or protected")
        )
    }

    @Test
    fun `abstract class not passed in body when one protected`() {
        assertLintErrors(
            """
                package com.github.johnnysc.practicetdd
                    
                abstract class Repository(protected val dataSource: DataSource) {
                
                   val page: Int
                }
            """.trimIndent(),
            LintViolation(line=5, col=4, detail="The page property must be private or protected")
        )
    }

    @Test
    fun `abstract class not passed in body when one private`() {
        assertLintErrors(
            """
                package com.github.johnnysc.practicetdd
                    
                abstract class Repository(private val dataSource: DataSource) {
                
                   val page: Int
                }
            """.trimIndent(),
            LintViolation(line=5, col=4, detail="The page property must be private or protected")
        )
    }
}
