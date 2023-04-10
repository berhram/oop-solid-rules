package com.velvet.rules

import com.velvet.rules.core.AbstractRule
import com.velvet.rules.core.canBeParent
import com.velvet.rules.core.isAbstractClass
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class InheritanceRule : AbstractRule("inheritance-rule") {

    private val allowedClasses = listOf(
        "Fragment", "Activity"
    )

    override fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (ktClass.superTypeListEntries.isEmpty()) {
            if (!ktClass.canBeParent()) {
                emit(
                    ktClass.startOffset,
                    "The class ${ktClass.name} must have been inherited",
                    false
                )
            }
        } else {
            ktClass.superTypeListEntries.firstOrNull { it.typeReference?.hasParentheses() == true }?.let {
                if (ktClass.isAbstractClass() && !allowedClasses.contains(it.name)) {
                    emit(
                        ktClass.startOffset,
                        "The class ${ktClass.name} should not been inherited from another class",
                        false
                    )
                }
            }
        }
    }
}
