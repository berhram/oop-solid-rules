package com.velvet.rules

import com.velvet.rules.core.AbstractRule
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class FunctionsRule : AbstractRule("functions-rule") {

    private val allowedMembers = 5

    private val retrofitAnnotation = listOf(
        "retrofit2.http.HTTP",
        "retrofit2.http.GET",
        "retrofit2.http.POST",
        "retrofit2.http.PUT",
        "retrofit2.http.PATCH",
        "retrofit2.http.DELETE",
        "retrofit2.http.OPTIONS",
        "retrofit2.http.HEAD"
    )

    override fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        val functions = ktClass.declarations.mapNotNull { it as? KtNamedFunction }
        functions.forEach {
            if (!it.hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
                emit(
                    it.startOffset,
                    "The fun ${it.name} must be overridden from interface",
                    false
                )
            }
        }
    }

    override fun visitInterface(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        val functions = ktClass.declarations.mapNotNull { it as? KtFunction }
        if (functions.size > allowedMembers) {
            emit(
                ktClass.startOffset,
                "The interface ${ktClass.name} must not contains more than $allowedMembers fun",
                false
            )
        }
        functions.forEach {
            if (it.bodyExpression != null) {
                emit(
                    it.startOffset, "The fun ${it.name} must not have default implementation", false
                )
            }
            if (it.valueParameters.size > allowedMembers && !it.annotations.any { annot -> annot.text in retrofitAnnotation }) {
                emit(
                    it.startOffset, "The fun ${it.name} must not have more than 5 args", false
                )
            }
        }
    }
}