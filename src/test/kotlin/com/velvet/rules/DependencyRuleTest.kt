package com.velvet.rules

import org.junit.jupiter.api.Test

class DependencyRuleTest : BaseTest(DependencyRule()) {

    @Test
    fun `valid properties`() {
        assertNoLintErrors(
            """
                package com.velvet.rules
                
                interface ProvideName {
                
                    fun provide(): String
                }

                class Person(private val provideName: ProvideName) {
                    
                    fun hello() = "Hello, my name is \$\{provideName.provide()\}"
                }  
            """
        )
    }

    @Test
    fun `invalid delegated`() {
        assertLintErrors(
            """
                package com.velvet.rules

                class Person(val id: String) {
    
                    val isAdult by lazy { AtomicBoolean() }
                }
            """
        )
    }

    @Test
    fun `invalid constructor`() {
        assertLintErrors(
            """
                package com.velvet.rules                

                class Person(val id: String) {
    
                    val isAdult = AtomicBoolean()
                }
            """
        )
    }
}
