package com.velvet.rules

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

class OopSolidRuleSetProvider : RuleSetProviderV3(RuleSetId("oop")) {

    override fun getRuleProviders(): Set<RuleProvider> = setOf(
        RuleProvider { EncapsulationRule() },
        RuleProvider { OopOnlyClassesRule() },
        RuleProvider { FunctionsRule() },
        RuleProvider { SortRule() }
    )
}
