package io.online.itbank.dto

import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CustomerDto {

    /**
     * Marker for grouping Validations on creation of a (new) Customer
     */
    interface CustomerCreation {}

    /**
     * Marker for grouping Validations on Update of a (new) Customer
     */
    interface CustomerUpdate {}

    @Min(0, groups = [CustomerCreation::class])
    var type: Int? = 0

    @NotBlank(groups = [CustomerCreation::class])
    var name: String? = null

    @NotBlank(groups = [CustomerCreation::class])
    var lastName: String? = null

    @Email(groups = [CustomerCreation::class, CustomerUpdate::class])
    @NotNull(groups = [CustomerCreation::class])
    var email: String? = null

}