package kr.co.postbox.controller.signUp

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.postbox.common.ApiResponse
import kr.co.postbox.config.exception.PostBoxException
import kr.co.postbox.dto.member.MemberSaveDTO
import kr.co.postbox.service.member.MemberService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@Api(tags = ["SignUp API"], description = "회원가입 api 리스트")
@RequestMapping("/api/sign")
class SignRestController {

    private val log = LoggerFactory.getLogger(SignRestController::class.java)

    @set:Autowired
    lateinit var memberService: MemberService

    @set:Autowired
    lateinit var messageSource: MessageSource

    @ApiOperation(value = "전화번호 가입유무 ", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value=["/check/{phoneNumber}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun duplicationPhoneNumber(@PathVariable("phoneNumber") phoneNumber:String): ApiResponse {
        log.info("SignUpRestController.duplicationPhoneNumber")
        log.debug(phoneNumber)
        return when (memberService.checkPhoneNumber(phoneNumber)) {
            0L -> ApiResponse.ok()
            else -> {
                ApiResponse.error(messageSource.getMessage("SIGNUP.ID.DUPLICATE", null, Locale.getDefault()))
            }
        }
    }

    @ApiOperation(value = "회원가입", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(value=["/register"],produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun memberRegister(memberSaveDTO: MemberSaveDTO) : ApiResponse{
        log.info("SignUpRestController.memberRegister")
        return when (memberService.checkPhoneNumber(memberSaveDTO.phoneNumber)) {
            0L -> ApiResponse.ok(memberService.register(memberSaveDTO))
            else -> throw PostBoxException("SIGNUP.ID.DUPLICATE")
        }
    }

}