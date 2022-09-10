package io.online.itbank.respository

import io.online.itbank.model.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, Long>