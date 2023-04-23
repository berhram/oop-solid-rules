package com.velvet.rules.core

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

abstract class AbstractRuleSetProvider(id: String) : RuleSetProviderV3(RuleSetId(id)) {

    abstract val rules: Set<Rule>

    override fun getRuleProviders(): Set<RuleProvider> = rules.mapTo(HashSet()) { RuleProvider { it } }
}