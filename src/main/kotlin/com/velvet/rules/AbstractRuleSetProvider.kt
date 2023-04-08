package com.velvet.rules

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.RuleProvider
import com.pinterest.ktlint.core.RuleSetProviderV2

abstract class AbstractRuleSetProvider(
    id: String, about: About
) : RuleSetProviderV2(id, about) {

    abstract val rules: Set<Rule>

    override fun getRuleProviders(): Set<RuleProvider> = rules.mapTo(HashSet()) { RuleProvider { it } }
}