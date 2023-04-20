package com.velvet.rules.core

import org.jetbrains.kotlin.psi.KtClass

abstract class AndroidRule(id: String) : AbstractRule(id) {

    private val skippedAnnotation = listOf("Entity")

    override fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (!ktClass.annotationEntries.any { it.typeReference?.text in skippedAnnotation }) {
            super.visitClass(ktClass, autoCorrect, emit)
        }
    }
}