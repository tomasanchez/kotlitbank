package io.online.itbank.services.generic

import io.online.itbank.dto.CustomerDto
import io.online.itbank.mapper.CustomerMapper
import io.online.itbank.model.Customer
import io.online.itbank.respository.CustomerRepository
import io.online.itbank.services.api.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GenericCustomerService : GenericPersistentEntityService<Customer>(), CustomerService {

    @Autowired
    lateinit var customerRepository: CustomerRepository

    val mapper: CustomerMapper = CustomerMapper()

    override val dao: CrudRepository<Customer, UUID>
        get() = customerRepository


    override fun save(dto: CustomerDto): Customer {
        val customer: Customer = mapper.create(dto)
        return save(customer)
    }
}