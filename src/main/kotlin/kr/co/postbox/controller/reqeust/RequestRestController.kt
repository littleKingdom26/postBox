package kr.co.postbox.controller.reqeust

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.postbox.common.ApiResponse
import kr.co.postbox.dto.authUser.AuthUserDTO
import kr.co.postbox.dto.request.RequestSaveDTO
import kr.co.postbox.service.reqeust.RequestService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@Api(tags = ["Reqeust API"], description = "의뢰 api 리스트")
@RequestMapping("/api/request")
class RequestRestController {

    private val log = LoggerFactory.getLogger(RequestRestController::class.java)

    @set:Autowired
    lateinit var requestService: RequestService

    @ApiOperation(value = "의뢰 등록", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(value = ["/save"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun save(requestSaveDTO: RequestSaveDTO, @ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO) : ApiResponse{
        log.info("RequestRestController.save")
        return ApiResponse.ok(requestService.save(requestSaveDTO))
    }



}