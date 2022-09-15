package kr.co.postbox.service.reqeust

import kr.co.postbox.code.CodeYn
import kr.co.postbox.code.RequestCategory
import kr.co.postbox.code.RequestSex
import kr.co.postbox.dto.authUser.AuthUserDTO
import kr.co.postbox.dto.request.RequestSaveDTO
import kr.co.postbox.repository.member.MemberRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
internal class RequestServiceTest{

    @Autowired
    lateinit var requestService: RequestService

    @Autowired
    lateinit var memberRepository: MemberRepository


    @Test
    @DisplayName("의뢰저장")
    fun save(){

        val title = "의뢰제목"

        val member = memberRepository.findById(1).get()

        val authUserDTO = AuthUserDTO(member.phoneNumber, "", member.memberKey ?: 0L, member.nickName, member.role)
        val save = requestService.save(
            RequestSaveDTO(
                title,
                RequestCategory.HELP,
                RequestSex.ALL,
                "디테일입니다.",
                2000L,
                CodeYn.N,
                null,
            ),
            authUserDTO
        )
        assertEquals(title,save.title)

    }
}