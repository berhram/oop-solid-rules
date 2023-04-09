package com.velvet.rules

import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.isAbstract
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class InheritanceRule : AbstractRule("inheritance-rule") {

    private val allowedAbstractClassesInheritedFrom = listOf(
        "Fragment", "Activity"
    )

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        node.psi.classOrNull()?.apply {
            if (superTypeListEntries.isEmpty()) {
                if (!canBeParent()) {
                    emit(
                        startOffset,
                        "The class $name must have been inherited",
                        false
                    )
                }
            } else {
                superTypeListEntries.firstNotNullOfOrNull { element -> element.classOrNull() }?.let { parent ->
                    if (isAbstract() && parent.isAbstract()) {
                        emit(
                            startOffset,
                            "The class is abstract and is inherited from an abstract class",
                            false
                        )
                    }
                }
            }
        }
    }
}
