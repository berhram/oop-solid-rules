package com.velvet.rules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import org.intellij.lang.annotations.Language

abstract class BaseTest(rule: Rule) {

    private val assertRule = assertThatRule { rule }

    protected fun assertNoLintErrors(@Language("kotlin") code: String) {
        assertRule(code).hasNoLintViolations()
    }

    protected fun assertLintErrors(@Language("kotlin") code: String) {
        assertRule(code).hasLintViolations()
    }
}
