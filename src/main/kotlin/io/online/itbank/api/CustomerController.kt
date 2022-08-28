package io.online.itbank.api

import io.online.itbank.dto.CustomerDto
import io.online.itbank.model.Customer
import io.online.itbank.services.api.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("/api/customers")
class CustomerController {

    @Autowired
    lateinit var customerService: CustomerService

    @GetMapping
    fun all(): MutableList<Customer>? = customerService.all

    @PostMapping
    fun save(@RequestBody @Validated(CustomerDto.CustomerCreation::class)
             dto: CustomerDto): ResponseEntity<Customer> {

        val customer: Customer = customerService.save(dto)
        val uri: URI =
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(customer.getId())
                        .toUri()
        return ResponseEntity.created(uri).body(customer)
    }

    @PutMapping("/{id}")
    @PatchMapping("/{id}")
    fun update(@RequestBody customer: Customer, @PathVariable id: UUID): ResponseEntity<Customer> {

        var toUpdate: Customer = customerService[id]
                ?: throw EntityNotFoundException("Customer not found")

        toUpdate.name = customer.name
        toUpdate.email = customer.email ?: toUpdate.email

        val customerUpdated: Customer = customerService.save(toUpdate)

        return ResponseEntity.ok(customerUpdated)
    }


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        customerService.delete(id)
        return ResponseEntity.noContent().build()
    }


}


