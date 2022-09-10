package io.online.itbank.services.generic

import io.online.itbank.model.Account
import io.online.itbank.respository.AccountRepository
import io.online.itbank.services.api.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service

@Service
class GenericAccountService : GenericPersistentEntityService<Account>(), AccountService {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    override val dao: CrudRepository<Account, Long>
        get() = accountRepository

}
