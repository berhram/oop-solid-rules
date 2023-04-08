package com.velvet.rules

import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.startOffset
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifierTypeOrDefault

class EncapsulationRule : AbstractRule("encapsulation-rule") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        node.psi.classOrNull()?.let { clasz ->
            val properties = clasz.getProperties()
            val parameters = clasz.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }
            properties.forEach {
                val visibility = it.visibilityModifierTypeOrDefault()
                if (visibility.isPublicOrInternal() && clasz.canBeParent()) {
                    emit(
                        it.startOffset,
                        "The property ${it.name} must be private or protected",
                        false
                    )
                } else if (visibility.isPublicOrInternal() || (visibility.isProtected() && !clasz.canBeParent())) {
                    emit(
                        it.startOffset,
                        "The property ${it.name} must be private",
                        false
                    )
                }
            }
            parameters?.forEach {
                val visibility = it.visibilityModifierTypeOrDefault()
                if (visibility.isPublicOrInternal() && clasz.canBeParent()) {
                    emit(
                        it.startOffset,
                        "The constructor argument ${it.name} must be private or protected",
                        false
                    )
                } else if ((visibility.isPublicOrInternal()) || (visibility.isProtected() && !clasz.canBeParent())) {
                    emit(
                        it.startOffset,
                        "The constructor argument ${it.name} must be private",
                        false
                    )
                }
            }
        }
    }
}
