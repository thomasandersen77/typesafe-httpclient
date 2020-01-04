package io.mistafry.tools.testtools

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EventStatusMetadataRepository : JpaRepository<EventStatusMetadata, Long> {

    @Query("""
        select esm from EventStatusMetadata esm where esm.id > :id
    """)
    fun queryByIdGreaterThan0(id: Long): List<EventStatusMetadataRepository>
}