package kr.co.postbox.dto.request

import io.swagger.annotations.ApiModelProperty
import kr.co.postbox.code.RequestCategory
import kr.co.postbox.code.RequestSex
import kr.co.postbox.controller.reqeust.RequestRestController
import kr.co.postbox.dto.aid.AidResultDTO
import kr.co.postbox.dto.member.MemberResultDTO
import kr.co.postbox.dto.selection.SelectionResultDTO
import kr.co.postbox.entity.request.TbRequest
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.linkTo
import java.time.LocalDateTime

data class RequestResultDTO(
    @ApiModelProperty(value = "의뢰요청키", required = true)
    var requestKey:Long?,
    @ApiModelProperty(value = "제목", required = true)
    var title:String,
    @ApiModelProperty(value = "카테고리", required = true)
    var category:String,
    @ApiModelProperty(value = "의뢰내용")
    var detail:String?,
    @ApiModelProperty(value = "성별", required = true)
    var sex: String,
    @ApiModelProperty(value = "협의여부", required = true)
    var negotiationYn:String,
    @ApiModelProperty(value = "가격", required = true)
    var price:Long,
    @ApiModelProperty(value = "동", required = false)
    var dong:String?,
    @ApiModelProperty(value = "시", required = false)
    var si: String?,
    @ApiModelProperty(value = "이미지 리스트")
    var imgFileList:List<RequestFileResultDTO>?,
    @ApiModelProperty(value="작성자")
    var member: MemberResultDTO,
    @ApiModelProperty(value="신청자목록")
    var aidList:List<AidResultDTO>?=null,
    @ApiModelProperty(value="선정자목록")
    var selectionList: List<SelectionResultDTO>?=null,
    @ApiModelProperty(value = "작성자")
    var regDt: LocalDateTime,
    @ApiModelProperty(value = "상세링크")
    var _links: Link? = null
) {
    constructor(tbRequest: TbRequest) : this(
        requestKey = tbRequest.requestKey,
        title = tbRequest.title,
        category = tbRequest.category,
        detail = tbRequest.detail,
        sex = tbRequest.sex,
        negotiationYn = tbRequest.negotiationYn,
        price = tbRequest.price,
        dong = tbRequest.dong,
        si = tbRequest.si,
        imgFileList = tbRequest.requestFileList?.map { RequestFileResultDTO(it) },
        member = MemberResultDTO(tbRequest.member),
        aidList = tbRequest.aidList?.map { AidResultDTO(it) },
        selectionList = tbRequest.selectionList?.map { SelectionResultDTO(it) },
        regDt = tbRequest.regDt,
        _links = linkTo<RequestRestController> { detail(tbRequest.requestKey ?: 0L) }.withSelfRel()
    )

    fun getCategoryName():String{
        return RequestCategory.valueOf(category).codeName
    }

    fun getSexName():String{
        return RequestSex.valueOf(sex).codeName
    }
}