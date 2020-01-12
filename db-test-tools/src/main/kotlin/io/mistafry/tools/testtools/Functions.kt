package io.mistafry.tools.testtools

import org.hibernate.internal.SessionImpl
import java.time.LocalDate
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

fun Any.log(): Unit {
    println(this.toString())
}

public inline fun <T> T.transaction(f: T.() -> Unit, em : EntityManager): T {
    println("begin")
    em.transaction.begin()
    f()
    em.transaction.commit()
    println("commit")
    return this
}

public inline fun <T> T.apply(block: T.() -> Unit): T {
    block();
    return this
}

fun main() {

/*
   "Hello World".log()
    123.log()
*/

    EventStatusMetadata(null, true, LocalDate.now(), LocalDate.now())
            .transaction(
                    { println("$dateCreated") },
                    SessionImpl(null,  null)
            )
}

