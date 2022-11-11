package kr.co.postbox.dto.request

import io.swagger.annotations.ApiModelProperty
import kr.co.postbox.code.RequestCategory
import kr.co.postbox.code.RequestSex
import kr.co.postbox.controller.reqeust.RequestRestController
import kr.co.postbox.dto.member.MemberResultDTO
import kr.co.postbox.entity.request.TbRequest
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.linkTo

data class RequestPageResultDTO(
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
    @ApiModelProperty(value="동", required = false)
    var dong:String?,
    @ApiModelProperty(value="시", required = false)
    var si:String?,
    @ApiModelProperty(value = "이미지 리스트")
    var imgFileList:List<RequestFileResultDTO>?,
    @ApiModelProperty(value="작성자")
    var member: MemberResultDTO,
    @ApiModelProperty(value = "지원자 숫자")
    var aidCount: Int?=0,
    @ApiModelProperty(value="신청자 숫지")
    var selectedCount: Int?=0,
    @ApiModelProperty(value="상세링크")
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
        aidCount = tbRequest.aidList?.size,
        selectedCount = tbRequest.selectionList?.size,
        _links =linkTo<RequestRestController> { detail(tbRequest.requestKey?:0L) }.withSelfRel()
    )

    fun getCategoryName():String{
        return RequestCategory.valueOf(category).codeName
    }

    fun getSexName():String{
        return RequestSex.valueOf(sex).codeName
    }

    fun getTitleImg():String? {
        return if(imgFileList.orEmpty().isNotEmpty()){
            imgFileList?.get(0)?.getRequestImgUrl()
        }else{
            null
        }
    }
}