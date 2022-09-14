package kr.co.postbox.controller.signUp

import kr.co.postbox.code.Sex
import org.junit.jupiter.api.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
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
internal class SignRestControllerTest{

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
    @Order(1)
    @DisplayName("전화번호_가입여부_체크")
    fun check_id() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/sign/check/1111"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Order(2)
    @DisplayName("회원가입")
    fun register(){
        val file = ClassPathResource("testFile/1.jpg").file
        val uploadFile = FileInputStream(file)
        val multipartFile = MockMultipartFile("profileImg", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)
        val info: MultiValueMap<String, String> = LinkedMultiValueMap()
        info.set("phoneNumber","010-1222-4444")
        info.set("name","윤태호")
        info.set("age","41")
        info.set("sex", Sex.MALE.name)
        info.set("address","경기도 수원시 권선구 호매실로165번길30")
        info.set("si","경기도 수원시")
        info.set("dong","호매실")
        info.set("introduce","")
        info.set("nickName","리틀킹덤")
        info.set("public","Y")
        info.set("marketingAgree","N")

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/sign/register")
                .file(multipartFile)
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Order(3)
    @DisplayName("회원가입_중복가입")
    fun register_duplication() {

        val info: MultiValueMap<String, String> = LinkedMultiValueMap()
        info.set("phoneNumber", "01022347292")
        info.set("name", "윤태")
        info.set("age", "41")
        info.set("sex", Sex.MALE.name)
        info.set("address", "경기도 수원시 권선구 호매실로165번길30")
        info.set("si", "경기도 수원시")
        info.set("dong", "호매실")
        info.set("introduce", "")
        info.set("nickName", "리틀킹덤")
        info.set("public", "Y")
        info.set("marketingAgree", "N")

        val file = ClassPathResource("testFile/1.jpg").file
        val uploadFile = FileInputStream(file)
        val multipartFile = MockMultipartFile("profileImg", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/sign/register")
                .file(multipartFile)
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
            .andDo(MockMvcResultHandlers.print())
    }

}