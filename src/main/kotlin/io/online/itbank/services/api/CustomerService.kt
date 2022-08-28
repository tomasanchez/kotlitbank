package io.online.itbank.services.api

import io.online.itbank.dto.CustomerDto
import io.online.itbank.model.Customer


interface CustomerService : PersistentEntityService<Customer> {
    fun save(dto: CustomerDto): Customer
}