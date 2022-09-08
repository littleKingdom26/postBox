package kr.co.postbox.service.member

import kr.co.postbox.code.Role
import kr.co.postbox.dto.member.MemberSaveDTO
import kr.co.postbox.entity.member.TbMember
import kr.co.postbox.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService {
    private val log = LoggerFactory.getLogger(MemberService::class.java)

    @set:Autowired
    lateinit var memberRepository: MemberRepository

    @set:Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun checkPhoneNumber(phoneNumber: String): Long {
        return memberRepository.countByPhoneNumber(phoneNumber.replace("-",""))
    }

    fun register(memberSaveDTO: MemberSaveDTO) : TbMember {

        var encoderPassword = passwordEncoder.encode(memberSaveDTO.memberPassword)

        return memberRepository.save(
            TbMember(
                memberSaveDTO.phoneNumber,
                memberSaveDTO.name,
                memberSaveDTO.age,
                memberSaveDTO.sex.name,
                memberSaveDTO.address,
                memberSaveDTO.si,
                memberSaveDTO.dong,
                memberSaveDTO.introduce,
                memberSaveDTO.nickName,
                memberSaveDTO.public.name,
                memberSaveDTO.marketingAgree.name,
                "0",
                encoderPassword,
                Role.USER.name,
                null
            )
        )
    }


}