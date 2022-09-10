package io.online.itbank.services.generic

import io.online.itbank.services.api.PersistentEntityService
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service

@Service
abstract class GenericPersistentEntityService<T : Any>
    : PersistentEntityService<T> {

    override fun save(entity: T): T {
        return dao.save(entity)
    }

    override fun delete(id: Long) {
        dao.deleteById(id)
    }

    override fun get(id: Long): T? {
        return dao.findById(id).orElse(null)
    }

    override val all: MutableList<T>
        get() {
            val returnList: MutableList<T> = ArrayList()
            dao.findAll().forEach { obj: T -> returnList.add(obj) }
            return returnList
        }

    abstract val dao: CrudRepository<T, Long>
}