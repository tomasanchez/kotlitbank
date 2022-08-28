package io.online.itbank.respository

import io.online.itbank.model.Account
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AccountRepository : CrudRepository<Account, UUID>