package com.velvet.rules

import com.velvet.rules.core.AbstractRule
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class DependencyRule : AbstractRule("dependency-rule") {

    override fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        ktClass.getProperties().forEach {
            if (it.hasInitializer() || it.hasDelegate()) {
                emit(
                    it.startOffset,
                    "The ${it.name} property must not be created in class",
                    false
                )
            }
        }
    }
}