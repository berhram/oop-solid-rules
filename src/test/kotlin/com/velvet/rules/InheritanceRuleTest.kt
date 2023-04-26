package com.velvet.rules

import com.pinterest.ktlint.test.LintViolation
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
            """.trimIndent()
        )
    }

    @Test
    fun `passed when interface`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                interface SomeClass

                class Repository : SomeClass
            """.trimIndent()
        )
    }

    @Test
    fun `passed when open class`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                open class SomeClass

                class Repository : SomeClass()
            """.trimIndent()
        )
    }

    @Test
    fun `passed when args`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                abstract class SomeClass(a: String, b: Boolean)

                class Repository(a: String, b: Boolean) : SomeClass(a, b)
            """.trimIndent()
        )
    }

    @Test
    fun `passed when abstract inherits allowed class`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                abstract class BaseFragment : Fragment()

                abstract class BaseView(a: String, b: Boolean) : View(a,b)

                abstract class BaseViewGroup : ViewGroup()

                abstract class BaseActivity : Activity()

                abstract class BaseViewModel : ViewModel()
            """.trimIndent()
        )
    }

    @Test
    fun `passed when abstract inherits interface`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                interface Repository

                abstract class AnotherAR : Repository
            """.trimIndent()
        )
    }

    @Test
    fun `passed because test`() {
        assertNoLintErrors(
            """
                package com.github.johnnysc.practicetdd

                class RepositoryTest
            """.trimIndent()
        )
    }

    @Test
    fun `no passed`() {
        assertLintErrors(
            """
                package com.github.johnnysc.practicetdd

                class Repository
            """.trimIndent(),
            LintViolation(line = 3, col = 1, detail = "The class Repository must be inherited")
        )
    }

    @Test
    fun `no passed if abstract inherits abstract`() {
        assertLintErrors(
            """
                package com.github.johnnysc.practicetdd

                abstract class AbstractRepository

                abstract class AnotherAR : AbstractRepository()

                abstract class AnotherBaseFragment : BaseFragment()
            """.trimIndent(),
            LintViolation(line = 5, col = 1, detail = "The class AnotherAR should not be inherited from another class"),
            LintViolation(
                line = 7,
                col = 1,
                detail = "The class AnotherBaseFragment should not be inherited from another class"
            )
        )
    }

    @Test
    fun `no passed if abstract inherits abstract 2`() {
        assertLintErrors(
            """
                package com.github.johnnysc.practicetdd

                abstract class AnotherBaseFragment : BaseFragment()
            """.trimIndent(), LintViolation(
                line = 3, col = 1, detail = "The class AnotherBaseFragment should not be inherited from another class"
            )
        )
    }
}