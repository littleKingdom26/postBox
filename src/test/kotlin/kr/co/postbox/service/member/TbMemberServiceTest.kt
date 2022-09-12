package kr.co.postbox.service.member

import kr.co.postbox.code.CodeYn
import kr.co.postbox.code.Sex
import kr.co.postbox.dto.member.MemberSaveDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
internal class TbMemberServiceTest{

    @Autowired
    private lateinit var memberService: MemberService


    @Test
    fun duplicationNumber(){
        val checkPhoneNumber = memberService.checkPhoneNumber("01022347292")

        println(checkPhoneNumber)
    }

    @Test
    @Order(1)
    @DisplayName("회원가입")
    fun register(){
        val phoneNumber = "01022347292"
        val memberSaveDTO = MemberSaveDTO(
            "윤태호",
            "01022347292",
            30,
            Sex.MALE,
            "경기도 수원시 권선구 호매실로165번길 30",
            "수원시 권선구",
            "호매실동",
            "메롱",
            "리틀킹덤",
            CodeYn.Y,
            CodeYn.Y,
            null
        )

        val register = memberService.register(memberSaveDTO)
        println(register.phoneNumber)
        assertEquals(register.phoneNumber,phoneNumber)
    }

}