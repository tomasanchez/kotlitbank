package io.online.itbank.services.generic

import io.online.itbank.services.api.PersistentEntityService
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
abstract class GenericPersistentEntityService<T>
    : PersistentEntityService<T> {

    override fun save(entity: T): T {
        return dao.save(entity)
    }

    override fun delete(id: UUID) {
        dao.deleteById(id)
    }

    override fun get(id: UUID): T? {
        return dao.findById(id).orElse(null)
    }

    override val all: MutableList<T>
        get() {
            val returnList: MutableList<T> = ArrayList()
            dao.findAll().forEach { obj: T -> returnList.add(obj) }
            return returnList
        }

    abstract val dao: CrudRepository<T, UUID>
}