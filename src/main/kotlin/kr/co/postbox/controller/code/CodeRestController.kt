package kr.co.postbox.controller.code

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.postbox.code.RequestCategory
import kr.co.postbox.code.RequestSex
import kr.co.postbox.code.Role
import kr.co.postbox.code.Sex
import kr.co.postbox.common.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/code")
@Api(tags = ["Code API"], description = "코드 조회 api 리스트")
class CodeRestController {
    private val log = LoggerFactory.getLogger(CodeRestController::class.java)


    @ApiOperation(value = "권한 코드 조회", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value = ["/role"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findRoleCode(): ApiResponse {
        log.info("CodeRestController.findRoleCode")
        return ApiResponse.okMessage(Role.values().associate { it.name to it.codeName }, "message")
    }

    @ApiOperation(value = "성별 코드 조회", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value = ["/sex"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findSexCode(): ApiResponse {
        log.info("CodeRestController.findSexCode")
        return ApiResponse.okMessage(Sex.values().associate { it.name to it.codeName }, "message")
    }

    @ApiOperation(value = "요청성별 코드 조회", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value = ["/requestSex"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findRequestSex(): ApiResponse {
        log.info("CodeRestController.findRequestSex")
        return ApiResponse.okMessage(RequestSex.values().associate { it.name to it.codeName }, "message")
    }

    @ApiOperation(value = "요청카테고리 코드 조회", notes = "## Request ##\n" + "[하위 Parameters 참고]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참고]\n\n\n\n")
    @GetMapping(value = ["/requestCategory"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findRequestCategory(): ApiResponse {
        log.info("CodeRestController.findRequestCategory")
        return ApiResponse.okMessage(RequestCategory.values().associate { it.name to it.codeName }, "message")
    }


}