package com.velvet.rules

import com.pinterest.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.parents
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class InheritanceRule : Rule("inheritance-rule") {

    private val allowedAbstractClassesInheritedFrom = listOf(
        "Fragment", "Activity"
    )

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (node.psi is KtClass) {
            val clazz = node.psi as KtClass
            if (clazz.parents.count() == 1) {
                emit(
                    clazz.startOffset,
                    "The class ${clazz.name} must have been inherited",
                    false
                )
            } else {
                if (clazz.isAbstractClass() && clazz.parent is KtClass) {
                    val parentClazz = clazz.parent as KtClass
                    if (
                        clazz.isAbstractClass() &&
                        parentClazz.isAbstractClass() &&
                        allowedAbstractClassesInheritedFrom.contains(
                            parentClazz.name
                        )
                    ) {
                        emit(
                            clazz.startOffset,
                            "The class is abstract and is inherited from an abstract class",
                            false
                        )
                    }
                }
            }
        }
    }

    private fun KtClass.isAbstractClass() = this.hasModifier(KtTokens.ABSTRACT_KEYWORD)
}