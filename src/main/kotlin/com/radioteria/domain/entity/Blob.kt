package com.radioteria.domain.entity

import javax.persistence.*

@Entity
@Table(name = "blobs")
data class Blob(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Long? = null,

        @Column(name = "content_type")
        var contentType: String,

        @Column(name = "size")
        var size: Long,

        @Column(name = "hash")
        var hash: String,

        @Column(name = "file_system")
        var fileSystem: String
)