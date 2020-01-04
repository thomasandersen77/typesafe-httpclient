@file:Suppress("JpaObjectClassSignatureInspection")

package io.mistafry.tools.testtools

import java.time.LocalDate
import javax.persistence.*

@Entity
data class EventStatusMetadata (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        @Column
        val status: Boolean,
        @Column
        val dateCreated: LocalDate,
        @Column
        val dateUpdated: LocalDate
)
