package com.velvet.rules

import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.isAbstract
import org.jetbrains.kotlin.psi.psiUtil.parents
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
        node.psi.classOrNull()?.let {
            if (!it.canBeParent()) {
                if (it.superTypeListEntries.isEmpty()) {
                    emit(
                        it.startOffset,
                        "The class ${it.name} must have been inherited",
                        false
                    )
                } else {
                    it.parent.classOrNull()?.let { parent ->
                        if (
                            it.isAbstract() &&
                            parent.isAbstract()
                            && !allowedAbstractClassesInheritedFrom.contains(parent.name)
                        ) {
                            emit(
                                it.startOffset,
                                "The class is abstract and is inherited from an abstract class",
                                false
                            )
                        }
                    }
                }
            }
        }
    }
}
