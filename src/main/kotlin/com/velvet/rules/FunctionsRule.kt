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
        "HTTP", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"
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
                    it.startOffset, "You must override the ${it.name} from the interface", false
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
                "The ${ktClass.name} interface must not contain more than $allowedMembers fun",
                false
            )
        }
        functions.forEach {
            if (it.bodyExpression != null) {
                emit(
                    it.startOffset, "The fun ${it.name} must not have a default implementation", false
                )
            }
            if (it.valueParameters.size > allowedMembers && !it.annotationEntries.any { annot ->
                    annot.typeReference?.text in retrofitAnnotation
                }) {
                emit(
                    it.startOffset, "The fun ${it.name} must not have more than 5 args", false
                )
            }
        }
    }
}