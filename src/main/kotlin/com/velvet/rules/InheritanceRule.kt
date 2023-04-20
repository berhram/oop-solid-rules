package com.velvet.rules

import com.velvet.rules.core.AbstractRule
import com.velvet.rules.core.canBeParent
import com.velvet.rules.core.isAbstractClass
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class InheritanceRule : AbstractRule("inheritance-rule") {

    private val allowedClasses = listOf(
        "Fragment", "Activity", "View", "ViewGroup", "ViewModel"
    )

    override fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (ktClass.superTypeListEntries.isEmpty() && ktClass.name?.endsWith("Test") == false) {
            if (!ktClass.canBeParent()) {
                emit(
                    ktClass.startOffset, "The class ${ktClass.name} must be inherited", false
                )
            }
        } else {
            ktClass.superTypeListEntries.firstOrNull {
                it is KtSuperTypeCallEntry
            }?.let {
                if (ktClass.isAbstractClass() && !allowedClasses.any { allowedClass ->
                        it.typeReference?.nameForReceiverLabel() == allowedClass
                    }) {
                    emit(
                        ktClass.startOffset,
                        "The class ${ktClass.name} should not be inherited from another class",
                        false
                    )
                }
            }
        }
    }
}
