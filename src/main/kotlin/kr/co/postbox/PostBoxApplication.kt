package kr.co.postbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "userAuditingConfig")
class PostBoxApplication

fun main(args: Array<String>) {
    runApplication<PostBoxApplication>(*args)
}
