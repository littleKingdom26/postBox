package kr.co.postbox.service.member

import kr.co.postbox.code.Path
import kr.co.postbox.code.Role
import kr.co.postbox.config.exception.PostBoxException
import kr.co.postbox.dto.authUser.AuthUserDTO
import kr.co.postbox.dto.member.MemberResultDTO
import kr.co.postbox.dto.member.MemberSaveDTO
import kr.co.postbox.dto.member.MemberUpdateDTO
import kr.co.postbox.entity.member.TbMemberFile
import kr.co.postbox.entity.member.TbMember
import kr.co.postbox.repository.file.MemberFileRepository
import kr.co.postbox.repository.member.MemberRepository
import kr.co.postbox.utils.delete
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
    lateinit var memberFileRepository: MemberFileRepository

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
            val fileResultDTO = memberSaveDTO.profileImg.save(Path.PROFILE.path, root)

            val postBoxFile = memberFileRepository.save(
                TbMemberFile(
                    memberSaveDTO.profileImg?.originalFilename ?: "",
                    fileResultDTO.fileName,
                    fileResultDTO.filePath,
                    fileResultDTO.fileSize ?: 0L
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


    @Transactional
    fun update(memberUpdateDTO: MemberUpdateDTO, authUserDTO: AuthUserDTO) : MemberResultDTO {

        val member = memberRepository.findById(authUserDTO.memberKey).orElseThrow { throw PostBoxException("MEMBER.NOT_FOUND") }

        member.update(memberUpdateDTO)

        if (memberUpdateDTO.profileImg != null) {
            val fileResultDTO = memberUpdateDTO.profileImg.save(Path.PROFILE.path, root)
            val postBoxFile = memberFileRepository.save(
                TbMemberFile(
                    memberUpdateDTO.profileImg?.originalFilename ?: "",
                    fileResultDTO.fileName,
                    fileResultDTO.filePath,
                    fileResultDTO.fileSize ?: 0L
                )
            )
            // 기존 파일 삭제
            member.profileImg?.also { memberFileRepository.delete(it) }
            member.profileImg?.delete(root)
            member.profileImg = postBoxFile
        }

        return MemberResultDTO(member)

    }

    @Transactional
    fun deleteProfileImg(authUserDTO: AuthUserDTO) {
        val member = memberRepository.findById(authUserDTO.memberKey).orElseThrow { throw PostBoxException("MEMBER.NOT_FOUND") }
        val profileImg = member.profileImg
        member.profileImg = null
        profileImg?.also{ memberFileRepository.delete(it)}
        profileImg?.delete(root)
    }


}