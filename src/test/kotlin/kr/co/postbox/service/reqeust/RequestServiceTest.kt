package kr.co.postbox.service.reqeust

import kr.co.postbox.code.CodeYn
import kr.co.postbox.code.RequestCategory
import kr.co.postbox.code.RequestSex
import kr.co.postbox.dto.request.RequestSaveDTO
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
    lateinit var reqeustService: RequestService


    @Test
    @DisplayName("의뢰저장")
    fun save(){

        val title = "의뢰제목"

        val save = reqeustService.save(
            RequestSaveDTO(
                title,
                RequestCategory.HELP,
                RequestSex.ALL,
                "디테일입니다.",
                2000L,
                CodeYn.N,
                null
            )
        )
        assertEquals(title,save.title)

    }
}