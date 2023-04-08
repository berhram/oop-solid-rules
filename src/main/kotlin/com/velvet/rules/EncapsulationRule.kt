package com.velvet.rules

import com.pinterest.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.isAbstract
import org.jetbrains.kotlin.psi.psiUtil.startOffset
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifierTypeOrDefault

class EncapsulationRule : Rule("encapsulation-rule") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (node.psi is KtClass) {
            val clazz = node.psi as KtClass
            val properties = clazz.getProperties()
            val constructor = clazz.primaryConstructor
            val classCanBeInherited =
                clazz.isEnum() || clazz.isSealed() || clazz.hasModifier(KtTokens.OPEN_KEYWORD) || clazz.isAbstract()
            properties.forEach {
                val visibility = it.visibilityModifierTypeOrDefault()
                if (visibility.isPublicOrInternal() && classCanBeInherited) {
                    emit(
                        it.startOffset,
                        "Property ${it.name} must be private or protected",
                        false
                    )
                } else if ((visibility.isPublicOrInternal() && !classCanBeInherited) || (visibility.isProtected() && !classCanBeInherited)) {
                    emit(
                        it.startOffset,
                        "Property ${it.name} must be private, because class can't be inherited",
                        false
                    )
                }
            }
            constructor?.valueParameters?.filter { it.hasValOrVar() }?.forEach {
                val visibility = it.visibilityModifierTypeOrDefault()
                if (visibility.isPublicOrInternal() && classCanBeInherited) {
                    emit(
                        it.startOffset,
                        "Constructor argument ${it.name} must be private or protected",
                        false
                    )
                } else if ((visibility.isPublicOrInternal() && !classCanBeInherited) || (visibility.isProtected() && !classCanBeInherited)) {
                    emit(
                        it.startOffset,
                        "Constructor argument ${it.name} must be private, because class can't be inherited",
                        false
                    )
                }
            }
        }
    }

    private fun KtModifierKeywordToken.isPublicOrInternal() =
        this == KtTokens.PUBLIC_KEYWORD || this == KtTokens.INTERNAL_KEYWORD

    private fun KtModifierKeywordToken.isProtected() = this == KtTokens.PROTECTED_KEYWORD
}
