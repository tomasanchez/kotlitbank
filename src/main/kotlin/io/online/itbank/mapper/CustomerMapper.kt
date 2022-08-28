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


}