package com.radioteria.domain.repository

import com.radioteria.domain.entity.Blob
import org.springframework.data.repository.Repository

interface BlobRepository : Repository<Blob, Long> {
    fun findById(id: Long): Blob?
    fun findByHash(hash: String): Blob?
    fun save(blob: Blob)
    fun delete(blob: Blob)
}