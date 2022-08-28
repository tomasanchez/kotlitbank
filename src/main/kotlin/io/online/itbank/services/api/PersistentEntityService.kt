package io.online.itbank.services.api

import java.util.*

interface PersistentEntityService<T> {

    fun save(entity: T): T

    fun delete(id: UUID)

    operator fun get(id: UUID): T?

    val all: MutableList<T>?
}