package com.velvet.rules

import com.velvet.rules.core.AbstractRule
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class SortRule : AbstractRule("oop:class-sort") {

    override fun visitClass(
        ktClass: KtClass,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        val elements = ktClass.body?.children.orEmpty()

        var seenConstructors = false
        var seenMethods = false
        var seenCompanion = false

        for (element in elements) {
            when (element) {
                is KtProperty -> {
                    if (seenConstructors || seenMethods || seenCompanion) {
                        emit(
                            element.startOffset, "The class element ${element.name} is not in the expected order", false
                        )
                    }
                }

                is KtInitializerList -> {
                    if (seenConstructors || seenMethods || seenCompanion) {
                        emit(
                            element.startOffset, "The init block is not in the expected order", false
                        )
                    }
                }

                is KtSecondaryConstructor -> {
                    seenConstructors = true
                    if (seenMethods || seenCompanion) {
                        emit(
                            element.startOffset, "The constructor is not in the expected order", false
                        )
                    }
                }

                is KtNamedFunction -> {
                    seenMethods = true
                    if (seenCompanion) {
                        emit(
                            element.startOffset, "The fun ${element.name} is not in the expected order", false
                        )
                    }
                }

                is KtObjectDeclaration -> {
                    if (element.isCompanion()) {
                        seenCompanion = true
                    } else {
                        if (seenConstructors || seenMethods || seenCompanion) {
                            emit(
                                element.startOffset,
                                "The class element ${element.name} is not in the expected order",
                                false
                            )
                        }
                    }
                }
            }
        }
    }
}