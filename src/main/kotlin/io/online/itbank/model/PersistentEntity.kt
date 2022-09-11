package io.online.itbank.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class PersistentEntity : Serializable {

    companion object {
        private const val serialVersionUID = -5554308939380869754L
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private val id: Long? = null

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var lastUpdatedAt: LocalDateTime? = null


    fun getId(): Long? = id

    @JsonIgnore
    fun isNew() = null == this.getId()

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as PersistentEntity

        return if (null == this.getId()) false
        else this.getId() == other.getId()
    }

    override fun hashCode(): Int {
        return 3744488
    }

    override fun toString() = "${this.javaClass.name} ($id)"

    @PrePersist
    protected fun onCreate() {
        val now: LocalDateTime = LocalDateTime.now()

        if (isNew()) createdAt = now
        lastUpdatedAt = now
    }

    @PreUpdate
    protected fun onUpdate() {
        lastUpdatedAt = LocalDateTime.now()
    }
}