package kr.co.postbox.service.user

import kr.co.postbox.config.exception.PostBoxException
import kr.co.postbox.entity.member.TbMember
import kr.co.postbox.repository.member.MemberRepository
import kr.co.postbox.dto.authUser.AuthUserDTO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService:UserDetailsService {
    private val log = LoggerFactory.getLogger(UserService::class.java)

    @set:Autowired lateinit var memberRepository: MemberRepository

    @set:Autowired lateinit var messageSource: MessageSource



    /**
     * 유저 정보 조회
     */
    override fun loadUserByUsername(username: String): UserDetails {
        // 유저 조회가 필요함
        log.debug("username : {}",username)
        val loginUser: TbMember? = memberRepository.findByPhoneNumber(username)
        if (loginUser != null) {
            val user: TbMember = loginUser
            return AuthUserDTO(user.phoneNumber, user.memberPassword, user.memberKey, user.nickName, user.role)
        }else{
            log.debug("message {}", messageSource.getMessage("LOGIN.ERROR", null, Locale.getDefault()))
            throw PostBoxException("LOGIN.ERROR")
        }
    }


}