package io.online.itbank.mapper

import io.online.itbank.dto.CustomerDto
import io.online.itbank.model.Customer
import io.online.itbank.model.CustomerType

class CustomerMapper {

    fun create(dto: CustomerDto): Customer =
            Customer(
                    type = CustomerType.values()[dto.type ?: 0],
                    name = dto.name!!,
                    lastName = dto.lastName!!,
                    email = dto.email!!,
            )

    fun update(dto: CustomerDto, customer: Customer, partial: Boolean = false): Customer {

        if (partial) {
            customer.type =
                    if (dto.type != null) CustomerType.values()[dto.type!!] else customer.type
            customer.name = dto.name ?: customer.name
            customer.lastName = dto.lastName ?: customer.lastName
            customer.email = dto.email ?: customer.email
        } else {
            customer.type = CustomerType.values()[dto.type ?: 0]
            customer.name = dto.name!!
            customer.lastName = dto.lastName!!
            customer.email = dto.email!!
        }

        return customer
    }


}