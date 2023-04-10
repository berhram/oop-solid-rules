package com.velvet.rules

import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.startOffset
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifierTypeOrDefault

class EncapsulationRule : AbstractRule("encapsulation-rule") {

    override fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        val properties = ktClass.getProperties()
        val parameters = ktClass.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }
        properties.forEach {
            val visibility = it.visibilityModifierTypeOrDefault()
            if (visibility.isPublicOrInternal() && ktClass.canBeParent()) {
                emit(
                    it.startOffset,
                    "The property ${it.name} must be private or protected",
                    false
                )
            } else if (visibility.isPublicOrInternal() || (visibility.isProtected() && !ktClass.canBeParent())) {
                emit(
                    it.startOffset,
                    "The property ${it.name} must be private",
                    false
                )
            }
        }
        parameters?.forEach {
            val visibility = it.visibilityModifierTypeOrDefault()
            if (visibility.isPublicOrInternal() && ktClass.canBeParent()) {
                emit(
                    it.startOffset,
                    "The constructor argument ${it.name} must be private or protected",
                    false
                )
            } else if ((visibility.isPublicOrInternal()) || (visibility.isProtected() && !ktClass.canBeParent())) {
                emit(
                    it.startOffset,
                    "The constructor argument ${it.name} must be private",
                    false
                )
            }
        }
    }
}
