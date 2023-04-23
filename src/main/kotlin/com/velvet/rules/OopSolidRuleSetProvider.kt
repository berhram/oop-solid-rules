package com.velvet.rules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.velvet.rules.core.AbstractRuleSetProvider

class OopSolidRuleSetProvider : AbstractRuleSetProvider("oop-solid-rules") {

    override val rules: Set<Rule> = setOf(
        EncapsulationRule(),
        InheritanceRule(),
        OopOnlyClassesRule(),
        FunctionsRule()
    )
}
