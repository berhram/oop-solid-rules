package com.velvet.rules

import com.pinterest.ktlint.test.RuleSetProviderTest

class OopSolidRuleSetProviderTest : RuleSetProviderTest(
    rulesetClass = OopSolidRuleSetProvider::class.java,
    packageName = "com.velvet.rules",
)