package com.velvet.rules

import com.pinterest.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtClass

abstract class AbstractRule(id: String) : Rule(id) {

    protected fun KtModifierKeywordToken.isPublicOrInternal() =
        this == KtTokens.PUBLIC_KEYWORD || this == KtTokens.INTERNAL_KEYWORD

    protected fun KtModifierKeywordToken.isProtected() = this == KtTokens.PROTECTED_KEYWORD

    protected fun PsiElement.classOrNull(): KtClass? = if (this is KtClass && !this.isInterface()) this else null

    protected fun KtClass.canBeParent() =
        this.isEnum() || this.isSealed() || this.hasModifier(KtTokens.OPEN_KEYWORD) || this.hasModifier(KtTokens.ABSTRACT_KEYWORD)
}
