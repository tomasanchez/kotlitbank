package io.online.itbank.respository

import io.online.itbank.model.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository : CrudRepository<Customer, UUID>