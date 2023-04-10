package com.velvet.rules.core

import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtClass

fun KtClass.canBeParent() =
    isEnum() || isSealed() || hasModifier(KtTokens.OPEN_KEYWORD) || hasModifier(KtTokens.ABSTRACT_KEYWORD)

fun KtClass.isAbstractClass() = hasModifier(KtTokens.ABSTRACT_KEYWORD)