package kr.co.postbox.controller.member

import io.swagger.annotations.Api
import kr.co.postbox.common.ApiResponse
import kr.co.postbox.dto.authUser.AuthUserDTO
import kr.co.postbox.service.member.MemberService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@Api(tags = ["Member API"], description = "회원 api 리스트")
@RequestMapping("/api/member")
class MemberRestController {

    private val log = LoggerFactory.getLogger(MemberRestController::class.java)

    @set:Autowired
    lateinit var memberService: MemberService


    // 프로필 정보 조회
    @GetMapping(value= ["/info"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun memberInfo(@ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO): ApiResponse {
        log.info("MemberRestController.memberInfo")
        log.debug(authUserDTO.memberKey.toString())
        return ApiResponse.ok(memberService.findById(authUserDTO.memberKey))
    }

    // 프로필 수정
}