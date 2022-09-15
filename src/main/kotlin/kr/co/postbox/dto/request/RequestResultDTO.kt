package kr.co.postbox.dto.request

import io.swagger.annotations.ApiModelProperty
import kr.co.postbox.code.RequestCategory
import kr.co.postbox.code.RequestSex
import kr.co.postbox.dto.member.MemberResultDTO
import kr.co.postbox.entity.request.TbRequest

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
    @ApiModelProperty(value = "이미지 리스트")
    var imgFileList:List<RequestFileResultDTO>?,
    @ApiModelProperty(value="작성자")
    var member: MemberResultDTO
) {
    constructor(tbRequest: TbRequest) : this(
        requestKey = tbRequest.requestKey,
        title = tbRequest.title,
        category = tbRequest.category,
        detail = tbRequest.detail,
        sex = tbRequest.sex,
        negotiationYn = tbRequest.negotiationYn,
        price = tbRequest.price,
        imgFileList = tbRequest.requestFileList?.map { RequestFileResultDTO(it) },
        member = MemberResultDTO(tbRequest.member)
    )

    fun getCategoryName():String{
        return RequestCategory.valueOf(category).codeName
    }

    fun getSexName():String{
        return RequestSex.valueOf(sex).codeName
    }
}