package kr.co.postbox.controller.reqeust

import kr.co.postbox.code.CodeYn
import kr.co.postbox.code.RequestCategory
import kr.co.postbox.code.RequestSex
import org.junit.jupiter.api.*
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
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@WithUserDetails("01022347292")
internal class RequestRestControllerTest{

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
    @DisplayName("의뢰등록")
    fun register(){
        val file = ClassPathResource("testFile/1.jpg").file
        val uploadFile = FileInputStream(file)
        val multipartFile = MockMultipartFile("requestFileList", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)
        val info: MultiValueMap<String, String> = LinkedMultiValueMap()
        info.set("title","도움요청!!")
        info.set("category",RequestCategory.HELP.name)
        info.set("sex",RequestSex.ALL.name)
        info.set("detail", "도와줘유 ~")
        info.set("price","10000")
        info.set("negotiationYn", CodeYn.N.name)

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/request/save")
                .file(multipartFile)
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("의뢰수정_없는_키")
    fun update_false(){
        val file = ClassPathResource("testFile/1.jpg").file
        val uploadFile = FileInputStream(file)
        val multipartFile = MockMultipartFile("requestFileList", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)
        val info: MultiValueMap<String, String> = LinkedMultiValueMap()
        info.set("title", "도움요청!!")
        info.set("category", RequestCategory.HELP.name)
        info.set("sex", RequestSex.ALL.name)
        info.set("detail", "도와줘유 ~")
        info.set("price", "10000")
        info.set("negotiationYn", CodeYn.N.name)

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/request/update/10000")
                .file(multipartFile)
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("의뢰수정")
    fun update() {
        val file = ClassPathResource("testFile/1.jpg").file
        val uploadFile = FileInputStream(file)
        val multipartFile = MockMultipartFile("requestFileList", file.name, MediaType.MULTIPART_FORM_DATA_VALUE, uploadFile)
        val info: MultiValueMap<String, String> = LinkedMultiValueMap()
        info.set("title", "수정합니다!!")
        info.set("category", RequestCategory.HELP.name)
        info.set("sex", RequestSex.FEMALE.name)
        info.set("detail", "수정!!")
        info.set("price", "100000")
        info.set("negotiationYn", CodeYn.Y.name)

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/request/update/2")
                .file(multipartFile)
                .params(info)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("상세조회")
    fun detail() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/request/detail/6")

        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("파일삭제")
    fun fileDelete(){
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/request/6/17")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }
}