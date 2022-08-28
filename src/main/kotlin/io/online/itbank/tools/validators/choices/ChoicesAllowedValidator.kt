package io.online.itbank.tools.validators.choices

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext


class ChoicesAllowedValidator : ConstraintValidator<ChoicesAllowed, String> {

    private lateinit var expectedChoices: List<String>
    private lateinit var returnMessage: String

    override fun initialize(constraintAnnotation: ChoicesAllowed?) {
        expectedChoices = constraintAnnotation!!.choices.toList()
        returnMessage = "${constraintAnnotation.message} $expectedChoices"

        super.initialize(constraintAnnotation)
    }

    override fun isValid(testChoice: String?, context: ConstraintValidatorContext?): Boolean {
        val valid: Boolean = validateChoice(testChoice)

        if (!valid) {

            context!!
                    .disableDefaultConstraintViolation()
            context!!
                    .buildConstraintViolationWithTemplate(returnMessage)
                    .addConstraintViolation()
        }

        return valid
    }

    private fun validateChoice(value: String?): Boolean {
        return try {
            expectedChoices.contains(value!!.uppercase())
        } catch (e: Exception) {
            value == null
        }
    }
}