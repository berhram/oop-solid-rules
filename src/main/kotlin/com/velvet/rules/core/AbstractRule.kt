package com.velvet.rules.core

import com.pinterest.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtClass

abstract class AbstractRule(id: String) : Rule(id) {

    private val skippedAnnotation = listOf("Entity")

    open fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) = Unit

    open fun visitInterface(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) = Unit

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        (node.psi as? KtClass)?.let { ktClass ->
            if (ktClass.isInterface()) {
                visitInterface(ktClass, autoCorrect, emit)
            } else if (!ktClass.annotationEntries.any { it.typeReference?.text in skippedAnnotation }) {
                visitClass(ktClass, autoCorrect, emit)
            }
        }
    }
}

