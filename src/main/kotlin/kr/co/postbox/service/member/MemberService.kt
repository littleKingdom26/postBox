package kr.co.postbox.service.member

import kr.co.postbox.code.Path
import kr.co.postbox.code.Role
import kr.co.postbox.config.exception.PostBoxException
import kr.co.postbox.dto.member.MemberResultDTO
import kr.co.postbox.dto.member.MemberSaveDTO
import kr.co.postbox.entity.file.TbPostBoxFile
import kr.co.postbox.entity.member.TbMember
import kr.co.postbox.repository.file.PostBoxFileRepository
import kr.co.postbox.repository.member.MemberRepository
import kr.co.postbox.utils.save
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService {
    private val log = LoggerFactory.getLogger(MemberService::class.java)

    @Value("\${file.upload.path}")
    lateinit var root: String

    @Value("\${postbox.default.password}")
    lateinit var password: String

    @set:Autowired
    lateinit var memberRepository: MemberRepository

    @set:Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @set:Autowired
    lateinit var postBoxFileRepository: PostBoxFileRepository

    fun checkPhoneNumber(phoneNumber: String): Long {
        return memberRepository.countByPhoneNumber(phoneNumber.replace("-",""))
    }

    /**
     * 회원등록
     */
    @Transactional
    fun register(memberSaveDTO: MemberSaveDTO) : MemberResultDTO {

        val encoderPassword = passwordEncoder.encode(password)

        val member = TbMember(
            memberSaveDTO.phoneNumber.replace("-",""),
            memberSaveDTO.name,
            memberSaveDTO.age,
            memberSaveDTO.sex.name,
            memberSaveDTO.address,
            memberSaveDTO.si,
            memberSaveDTO.dong,
            memberSaveDTO.introduce,
            memberSaveDTO.nickName,
            memberSaveDTO.nickNameYn.name,
            memberSaveDTO.publicYn.name,
            memberSaveDTO.marketingAgree.name,
            "0",
            encoderPassword,
            Role.USER.name,
            null
        )

        if (memberSaveDTO.profileImg != null) {
            val save = memberSaveDTO.profileImg.save(Path.PROFILE.path, root)

            val postBoxFile = postBoxFileRepository.save(
                TbPostBoxFile(
                    memberSaveDTO.profileImg?.originalFilename ?: "",
                    save.fileName,
                    save.filePath,
                    save.fileSize ?: 0L
                )
            )
            member.profileImg=postBoxFile
        }

        return MemberResultDTO(memberRepository.save(member))

    }

    /**
    * 회원정보 조회
     */
    fun findById(memberKey: Long) : MemberResultDTO {
        val member = memberRepository.findById(memberKey).orElseThrow {
            throw PostBoxException("MEMBER.NOT_FOUND")
        }
        return MemberResultDTO(member)
    }


}