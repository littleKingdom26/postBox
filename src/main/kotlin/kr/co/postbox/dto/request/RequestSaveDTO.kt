package kr.co.postbox.dto.request

import io.swagger.annotations.ApiModelProperty
import kr.co.postbox.code.CodeYn
import kr.co.postbox.code.RequestCategory
import kr.co.postbox.code.RequestSex
import org.springframework.web.multipart.MultipartFile

data class RequestSaveDTO(
    @ApiModelProperty(value = "제목", required = true)
    val title:String,
    @ApiModelProperty(value = "카테고리", required = true)
    val category: RequestCategory,
    @ApiModelProperty(value = "요청성별", required = true)
    val sex: RequestSex,
    @ApiModelProperty(value = "상세 의뢰 내용")
    val detail:String?,
    @ApiModelProperty(value = "금액")
    val price:Long?,
    @ApiModelProperty(value = "협의하기")
    val negotiationYn: CodeYn,
    @ApiModelProperty(value = "사진목록")
    var requestFileList: List<MultipartFile>?
    )