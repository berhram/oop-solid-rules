package com.velvet.rules.core

import org.jetbrains.kotlin.lexer.KtModifierKeywordToken
import org.jetbrains.kotlin.lexer.KtTokens

fun KtModifierKeywordToken.isPublicOrInternal() =
    this == KtTokens.PUBLIC_KEYWORD || this == KtTokens.INTERNAL_KEYWORD

fun KtModifierKeywordToken.isProtected() = this == KtTokens.PROTECTED_KEYWORD
