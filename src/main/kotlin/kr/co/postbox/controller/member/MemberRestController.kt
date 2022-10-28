package kr.co.postbox.controller.member

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.postbox.common.ApiResponse
import kr.co.postbox.dto.authUser.AuthUserDTO
import kr.co.postbox.dto.member.MemberUpdateDTO
import kr.co.postbox.service.member.MemberService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import java.util.*

@RestController
@Api(tags = ["Member API"], description = "회원 api 리스트")
@RequestMapping("/api/member")
class MemberRestController {

    private val log = LoggerFactory.getLogger(MemberRestController::class.java)

    @set:Autowired
    lateinit var memberService: MemberService

    @set:Autowired
    lateinit var messageSource: MessageSource


    // 프로필 정보 조회
    @ApiOperation(value="회원 정보조회",notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value= ["/info"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun memberInfo(@ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO): ApiResponse {
        log.info("MemberRestController.memberInfo")
        log.debug(authUserDTO.memberKey.toString())
        return ApiResponse.ok(memberService.findById(authUserDTO.memberKey))
    }

    // 프로필 수정

    @ApiOperation(value="회원 정보 수정",notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(value=["/update"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun memberUpdate(memberUpdateDTO: MemberUpdateDTO,@ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO):ApiResponse{
        log.info("MemberRestController.memberUpdate")
        return ApiResponse.ok(memberService.update(memberUpdateDTO, authUserDTO))
    }

    @ApiOperation(value="회원 파일 삭제", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @DeleteMapping(value=["/delete/memberFile"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fileDelete(@ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO): ApiResponse {
        log.info("MemberRestController.fileDelete")
        memberService.deleteProfileImg(authUserDTO)
        return ApiResponse.okMessage(message = messageSource.getMessage("MESSAGE.DELETE",null, Locale.getDefault()) )
    }
}