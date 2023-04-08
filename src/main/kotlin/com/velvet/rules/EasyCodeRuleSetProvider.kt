package com.velvet.rules

import com.pinterest.ktlint.core.Rule

class EasyCodeRuleSetProvider : AbstractRuleSetProvider("oop-solid-rules", NO_ABOUT) {

    override val rules: Set<Rule> = setOf(
        EncapsulationRule(),
        InheritanceRule()
    )
}
