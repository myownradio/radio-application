package com.radioteria.web.request.annotation

import com.radioteria.domain.repository.UserRepository
import org.springframework.stereotype.Component
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@Component
class AvailableEmailValidator(val userRepository: UserRepository) : ConstraintValidator<AvailableEmail, String> {

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        return userRepository.findByEmail(value) == null
    }

    override fun initialize(constraintAnnotation: AvailableEmail) {
        //
    }

}