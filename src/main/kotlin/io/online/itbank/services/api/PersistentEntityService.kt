package io.online.itbank.services.api

interface PersistentEntityService<T> {

    fun save(entity: T): T

    fun delete(id: Long)

    operator fun get(id: Long): T?

    val all: MutableList<T>?
}