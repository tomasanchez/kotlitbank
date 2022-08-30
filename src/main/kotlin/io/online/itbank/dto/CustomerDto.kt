package io.online.itbank.dto

import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CustomerDto {

    /**
     * Marker for grouping Validations on creation of a (new) Customer
     *  or fully replacing an existing Customer
     */
    interface CustomerCreation {}

    /**
     * Marker for grouping Validations on Partially Update of an existing Customer
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