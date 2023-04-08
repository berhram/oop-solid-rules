package com.velvet.rules

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.RuleProvider
import com.pinterest.ktlint.test.lint
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertTrue

abstract class BaseTest(rule: Rule) {

    private val provider = setOf(RuleProvider { rule })

    protected fun assertNoLintErrors(@Language("kotlin") code: String) {
        val errors = provider.lint(code.trimIndent())
        assertTrue(errors.isEmpty(), errors.toString())
    }

    protected fun assertLintErrors(@Language("kotlin") code: String) {
        val errors = provider.lint(code.trimIndent())
        assertTrue(errors.isNotEmpty(), errors.toString())
    }
}
