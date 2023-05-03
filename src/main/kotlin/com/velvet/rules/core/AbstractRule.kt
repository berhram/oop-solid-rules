package com.velvet.rules.core

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtClass

abstract class AbstractRule(id: String) : Rule(
    RuleId(id), About(
        maintainer = "berhram",
        repositoryUrl = "https://github.com/berhram/oop-solid-rules",
        issueTrackerUrl = "https://github.com/berhram/oop-solid-rules/issues"
    )
) {

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
            if (ktClass.name?.endsWith("Test") == false) {
                if (ktClass.isInterface()) {
                    visitInterface(ktClass, autoCorrect, emit)
                } else if (!ktClass.annotationEntries.any { it.typeReference?.text in skippedAnnotation }) {
                    visitClass(ktClass, autoCorrect, emit)
                }
            }
        }
    }
}

