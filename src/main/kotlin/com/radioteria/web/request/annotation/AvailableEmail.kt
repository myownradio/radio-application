package com.radioteria.web.request.annotation

import javax.validation.Constraint

@Constraint(validatedBy = arrayOf(AvailableEmailValidator::class))
annotation class AvailableEmail(val message: String = "This email is not available")