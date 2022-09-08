package kr.co.postbox.config.jpa

import kr.co.postbox.dto.authUser.AuthUserDTO
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserAuditingConfig: AuditorAware<String>{
    override fun getCurrentAuditor(): Optional<String> {

        var authentication: Authentication? = SecurityContextHolder.getContext()?.authentication
        return if (authentication?.principal is AuthUserDTO) {
            val authUserDTO: AuthUserDTO = authentication.principal as AuthUserDTO
            Optional.of(authUserDTO.phoneNumber)
        } else {
            Optional.of("SYSTEM")
        }

    }

}