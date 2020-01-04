package io.mistafry.tools.testtools

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
class TestToolsApplicationTests {

	@Autowired lateinit var em : TestEntityManager
	@Autowired lateinit var repository: EventStatusMetadataRepository

	@Test
	fun `save and find entity with EntityManager`() {
		val savedEntity = em.persist(EventStatusMetadata(null, true, LocalDate.now(), LocalDate.now()))
		assertNotNull(savedEntity)

		val findEntity = em.find(EventStatusMetadata::class.java, 1L)
		assertNotNull(findEntity)
	}

	@Test
	fun `save and find entity with JpaRepository`() {
		val savedEntity = repository.save(EventStatusMetadata(null, true, LocalDate.now(), LocalDate.now()))
		assertNotNull(savedEntity)

		val findEntity = repository.findById(1L)
		assertNotNull(findEntity)

		val idGreaterThan0 = repository.queryByIdGreaterThan0(0)
		assertNotNull(idGreaterThan0);
	}
}
