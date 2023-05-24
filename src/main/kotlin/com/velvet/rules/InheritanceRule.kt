package com.velvet.rules

import com.velvet.rules.core.AbstractRule
import com.velvet.rules.core.isAbstractClass
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class InheritanceRule : AbstractRule("oop:inheritance") {

    private val allowedClasses = listOf(
        "Fragment", "Activity", "View", "ViewGroup", "ViewModel", "RoomDatabase", "ViewHolder", "Callback"
    )

    override fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        ktClass.superTypeListEntries.firstOrNull {
            it is KtSuperTypeCallEntry
        }?.let {
            if (ktClass.isAbstractClass() && !allowedClasses.any { allowedClass ->
                    it.typeReference?.nameForReceiverLabel() == allowedClass
                }) {
                emit(
                    ktClass.startOffset, "The class ${ktClass.name} should not be inherited from another class", false
                )
            }
        }
    }
}
