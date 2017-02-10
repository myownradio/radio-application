package com.radioteria.domain.entity

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
        var content: Blob
)