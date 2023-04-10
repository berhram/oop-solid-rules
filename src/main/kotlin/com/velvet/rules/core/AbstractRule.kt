package com.velvet.rules.core

import com.pinterest.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtClass

abstract class AbstractRule(id: String) : Rule(id) {

    open fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) = Unit

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        val psi = node.psi
        val ktClass: KtClass? = if (psi is KtClass && !psi.isInterface()) psi else null
        ktClass?.let {
            visitClass(it, autoCorrect, emit)
        }
    }
}
