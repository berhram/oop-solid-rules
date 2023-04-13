package com.velvet.rules

import com.pinterest.ktlint.core.Rule
import com.velvet.rules.core.AbstractRuleSetProvider

class OopSolidRuleSetProvider : AbstractRuleSetProvider("oop-solid-rules", NO_ABOUT) {

    override val rules: Set<Rule> = setOf(
        EncapsulationRule(),
        InheritanceRule(),
        OopOnlyClassesRule(),
        FunctionsRule(),
        DependencyRule()
    )
}
