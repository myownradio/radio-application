package com.radioteria.domain.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "files")
data class File(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Long? = null,

        var name: String,

        @ManyToOne(targetEntity = Blob::class)
        @JoinColumn(name = "blob_id")
        var blob: Blob,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "created_at", nullable = false)
        var createdAt: Date = Date(),

        @Column(name = "is_permanent")
        var isPermanent: Boolean = false
)