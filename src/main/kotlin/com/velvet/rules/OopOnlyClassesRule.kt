package com.velvet.rules

import com.velvet.rules.core.AbstractRule
import com.velvet.rules.core.canBeParent
import com.velvet.rules.core.isAbstractClass
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class OopOnlyClassesRule : AbstractRule("only-oop-rules") {

    override fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (ktClass.canBeParent() && !ktClass.isAbstractClass()) {
            emit(
                ktClass.startOffset, "Do not use enum, sealed or open class", false
            )
        }
    }
}