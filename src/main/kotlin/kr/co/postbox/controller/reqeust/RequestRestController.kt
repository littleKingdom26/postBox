package kr.co.postbox.controller.reqeust

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.postbox.common.ApiResponse
import kr.co.postbox.dto.authUser.AuthUserDTO
import kr.co.postbox.dto.request.RequestSaveDTO
import kr.co.postbox.dto.request.RequestSearchDTO
import kr.co.postbox.dto.request.RequestUpdateDTO
import kr.co.postbox.service.reqeust.RequestService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import java.util.*

@RestController
@Api(tags = ["request API"], description = "의뢰 api 리스트")
@RequestMapping("/api/request")
class RequestRestController {

    private val log = LoggerFactory.getLogger(RequestRestController::class.java)

    @set:Autowired
    lateinit var requestService: RequestService

    @set:Autowired
    lateinit var messageSource: MessageSource

    @ApiOperation(value = "의뢰 등록", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(value = ["/save"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun save(requestSaveDTO: RequestSaveDTO, @ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO) : ApiResponse{
        log.info("RequestRestController.save")
        return ApiResponse.ok(requestService.save(requestSaveDTO,authUserDTO))
    }

    //수정 [파일 추가]
    @ApiOperation(value = "의뢰 수정", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(value = ["/update/{requestKey}"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(requestUpdateDTO: RequestUpdateDTO,@PathVariable("requestKey") requestKey: Long, @ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO) : ApiResponse{
        log.info("RequestRestController.update")
        requestUpdateDTO.requestKey = requestKey
        return ApiResponse.ok(requestService.update(requestUpdateDTO,authUserDTO))
    }

    // 상세보기
    @ApiOperation(value="의뢰 상세보기",notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value=["/detail/{requestKey}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun detail(@PathVariable("requestKey") requestKey: Long) : ApiResponse{
        log.info("RequestRestController.detail")
        return ApiResponse.ok(requestService.findByRequest(requestKey))
    }

    // 파일 삭제
    @ApiOperation(value="의뢰파일삭제",notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @DeleteMapping(value=["/{requestKey}/{requestFileKey}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun requestFileDelete(@PathVariable("requestKey") requestKey: Long , @PathVariable("requestFileKey") requestFileKey:Long, @ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO):ApiResponse{
        log.info("RequestRestController.requestFileDelete")
        requestService.deleteRequestFile(authUserDTO,requestKey,requestFileKey)
        return ApiResponse.okMessage(message = messageSource.getMessage("MESSAGE.DELETE", null, Locale.getDefault()))
    }

    @ApiOperation(value = "의뢰 목록 조회", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value = ["/page"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun requestPage(requestSearchDTO: RequestSearchDTO) : ApiResponse{
        log.info("RequestRestController.requestPage")
        return ApiResponse.ok(requestService.findByPage(requestSearchDTO))
    }

    // 삭제
    @ApiOperation(value="의뢰 삭제", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @DeleteMapping(value=["/{requestKey}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteRequest(@PathVariable("requestKey") requestKey: Long,@ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO): ApiResponse{
        log.info("RequestRestController.deleteRequest")
        requestService.deleteRequest(requestKey,authUserDTO)
        return ApiResponse.ok()
    }

    //의뢰신청
    @ApiOperation(value = "의뢰 신청", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(value = ["/apply/{requestKey}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun applyAid(@PathVariable("requestKey") requestKey: Long, @ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO): ApiResponse{
        log.info("RequestRestController.applyRequest")
        return ApiResponse.ok(requestService.applyAid(requestKey, authUserDTO))
    }

    //신청자 선정
    @ApiOperation(value = "신청자 선정", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @PostMapping(value = ["/selection/{aidKey}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun selectionAid(@PathVariable("aidKey") aidKey:Long, @ApiIgnore @AuthenticationPrincipal authUserDTO: AuthUserDTO): ApiResponse{
        log.info("RequestRestController.selectionAid")
        return ApiResponse.ok(requestService.selectionAid(aidKey, authUserDTO))
    }

    // 신청자 상세 보기
    @ApiOperation(value = "의뢰 상세보기", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value = ["/aid/{requestKey}/{aidKey}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun aidDetail(@PathVariable("requestKey") requestKey: Long,@PathVariable("aidKey") aidKey: Long): ApiResponse {
        log.info("RequestRestController.aidDetail")
        return ApiResponse.ok(requestService.findByAidDetail(requestKey,aidKey))
    }






}