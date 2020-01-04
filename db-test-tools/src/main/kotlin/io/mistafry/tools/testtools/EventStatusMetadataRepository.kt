package io.mistafry.tools.testtools

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventStatusMetadataRepository : JpaRepository<EventStatusMetadata, Long> {
}