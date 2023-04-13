package com.velvet.rules

import org.junit.jupiter.api.Test

class FunctionsRuleTest : BaseTest(FunctionsRule()) {

    @Test
    fun `invalid too many fun in interface`() {
        assertLintErrors(
            """
                package com.velvet.rules

                interface Person {
                    
                    fun hello()
                    
                    fun hello2()
                    
                    fun hello3()
                    
                    fun hello4()
                    
                    fun hello5()
                    
                    fun hello6()
                }
            """
        )
    }

    @Test
    fun `invalid too many arg in fun`() {
        assertLintErrors(
            """
                package com.velvet.rules

                interface Person {
                    
                    fun hello(a: String, b: String, c: String, d: String, e: String, f: String)
                }
            """
        )
    }

    @Test
    fun `invalid no override method in class`() {
        assertLintErrors(
            """
                package com.velvet.rules

                class Person {
                    
                    fun hello(a: String, b: String)
                }
            """
        )
    }

    @Test
    fun `invalid override method in class`() {
        assertNoLintErrors(
            """
                package com.velvet.rules

                class Person : SayHello {
                    
                    override fun hello(a: String, b: String)
                }
            """
        )
    }

    @Test
    fun `valid no too many fun in interface`() {
        assertNoLintErrors(
            """
                package com.velvet.rules

                interface Person {
                    
                    fun hello()
                    
                    fun hello2()
                    
                    fun hello3()
                    
                    fun hello4()
                    
                    fun hello5()
                }
            """
        )
    }

    @Test
    fun `valid no too arg in fun`() {
        assertNoLintErrors(
            """
                package com.velvet.rules

                interface Person {
                    
                    fun hello(a: String, b: String, c: String, d: String, e: String)
                }
            """
        )
    }

    @Test
    fun `valid no too many args in fun because retrofit`() {
        assertNoLintErrors(
            """
                package com.velvet.rules

                import retrofit2.http.GET
                import retrofit2.http.POST
                import retrofit2.http.PUT
                import retrofit2.http.PATCH
                import retrofit2.http.DELETE
                import retrofit2.http.OPTIONS
                import retrofit2.http.HEAD
                import retrofit2.http.HTTP

                interface Service {
                    
                    @GET("hello")
                    fun hello(a: String, b: String, c: String, d: String, e: String, f: String, g: String)
                    
                    @DELETE("hello")
                    fun hello2(a: String, b: String, c: String, d: String, e: String, f: String, g: String)
                    
                    @POST("hello")
                    fun hello3(a: String, b: String, c: String, d: String, e: String, f: String, g: String)
                    
                    @PUT("hello")
                    fun hello4(a: String, b: String, c: String, d: String, e: String, f: String, g: String)
                }

                interface Service2 {
                     
                    @PATCH("hello")
                    fun hello5(a: String, b: String, c: String, d: String, e: String, f: String, g: String) 
                    
                    @HEAD("hello")
                    fun hello6(a: String, b: String, c: String, d: String, e: String, f: String, g: String)
                    
                    @OPTIONS("hello")
                    fun hello7(a: String, b: String, c: String, d: String, e: String, f: String, g: String)
                    
                    @HTTP("hello")
                    fun hello8(a: String, b: String, c: String, d: String, e: String, f: String, g: String)
                }
            """
        )
    }
}