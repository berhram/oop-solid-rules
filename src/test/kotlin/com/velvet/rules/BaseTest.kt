package com.velvet.rules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.test.KtLintAssertThat
import com.pinterest.ktlint.test.LintViolation
import org.intellij.lang.annotations.Language

abstract class BaseTest(private val rule: Rule) {

    private val assertRule = KtLintAssertThat.assertThatRule { rule }

    protected fun assertNoLintErrors(@Language("kotlin") code: String) {
        assertRule(code).hasNoLintViolationsForRuleId(rule.ruleId)

    }

    protected fun assertLintErrors(@Language("kotlin") code: String, vararg violations: LintViolation) {
        assertRule(code).hasLintViolationsWithoutAutoCorrect(*violations)
    }
}
