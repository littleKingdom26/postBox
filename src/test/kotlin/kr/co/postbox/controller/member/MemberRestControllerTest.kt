package kr.co.postbox.controller.member

import kr.co.postbox.code.CodeYn
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.io.FileInputStream
import javax.transaction.Transactional

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation::class)
@WithUserDetails("01022347292")
internal class MemberRestControllerTest{
    lateinit var mockMvc: MockMvc

    @set:Autowired
    lateinit var ctx: WebApplicationContext


    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .apply { SecurityMockMvcConfigurers.springSecurity() }
            .build()
    }


    @Test
    @DisplayName("회원정보조회")
    fun memberInfo(){
        mockMvc.perform(MockMvcRequestBuilders.get("/api/member/info"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("회원정보 수정_파일_같이수정")
    fun memberUpdateAddFile(){
        val file = ClassPathResource("testFile/2.jpg").file
        val uploadFile = FileInputStream(file)
        val multipartFile = MockMultipartFile("profileImg", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)
        val info: MultiValueMap<String, String> = LinkedMultiValueMap()

        info.set("introduce", "자기소개 수정해봅니다.")
        info.set("nickName", "테리")
        info.set("publicYn", CodeYn.Y.name)
        info.set("nickNameYn",CodeYn.N.name)

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/member/update")
                .file(multipartFile)
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("회원정보 수정")
    fun memberUpdateNoFile() {

        val info: MultiValueMap<String, String> = LinkedMultiValueMap()

        info.set("introduce", "자기소개 수정해봅니다.")
        info.set("nickName", "테리")
        info.set("publicYn", CodeYn.Y.name)
        info.set("nickNameYn", CodeYn.N.name)

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/member/update")
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("회원프로필 삭제")
    fun memberFileDelete(){
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/member/delete/memberFile"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

}