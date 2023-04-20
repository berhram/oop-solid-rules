package com.velvet.rules

import com.velvet.rules.core.AbstractRule
import com.velvet.rules.core.canBeParent
import com.velvet.rules.core.isProtected
import com.velvet.rules.core.isPublicOrInternal
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.startOffset
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifierTypeOrDefault

class EncapsulationRule : AbstractRule("encapsulation-rule") {

    override fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        val properties = ktClass.getProperties().filter { !it.hasModifier(KtTokens.OVERRIDE_KEYWORD) }
        val parameters = ktClass.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }
            ?.filter { !it.hasModifier(KtTokens.OVERRIDE_KEYWORD) }
        properties.forEach {
            val visibility = it.visibilityModifierTypeOrDefault()
            if (visibility.isPublicOrInternal() && ktClass.canBeParent()) {
                emit(
                    it.startOffset, "The ${it.name} property must be private or protected", false
                )
            } else if (visibility.isPublicOrInternal() || (visibility.isProtected() && !ktClass.canBeParent())) {
                emit(
                    it.startOffset, "The ${it.name} property must be private", false
                )
            }
        }
        parameters?.forEach {
            val visibility = it.visibilityModifierTypeOrDefault()
            if (visibility.isPublicOrInternal() && ktClass.canBeParent()) {
                emit(
                    it.startOffset, "The constructor argument ${it.name} must be private or protected", false
                )
            } else if ((visibility.isPublicOrInternal()) || (visibility.isProtected() && !ktClass.canBeParent())) {
                emit(
                    it.startOffset, "The constructor argument ${it.name} must be private", false
                )
            }
        }
    }
}
