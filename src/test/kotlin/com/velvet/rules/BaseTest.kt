package com.velvet.rules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.test.KtLintAssertThat
import org.intellij.lang.annotations.Language

abstract class BaseTest(rule: Rule) {

    private val provider = RuleProvider { rule }

    protected fun assertNoLintErrors(@Language("kotlin") code: String) {
        KtLintAssertThat(
            ruleProvider = provider,
            code = code,
            additionalRuleProviders = mutableSetOf(),
            editorConfigProperties = mutableSetOf()
        ).hasNoLintViolations()
    }

    protected fun assertLintErrors(@Language("kotlin") code: String) {
        KtLintAssertThat(
            ruleProvider = provider,
            code = code,
            additionalRuleProviders = mutableSetOf(),
            editorConfigProperties = mutableSetOf()
        ).hasLintViolations()
    }
}
