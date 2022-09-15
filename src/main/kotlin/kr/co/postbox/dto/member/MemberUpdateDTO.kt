package kr.co.postbox.dto.member

import io.swagger.annotations.ApiModelProperty
import kr.co.postbox.code.CodeYn
import org.springframework.web.multipart.MultipartFile

data class MemberUpdateDTO (

    @ApiModelProperty(value = "자기소개")
    var introduce:String?,
    @ApiModelProperty(value = "닉네임")
    var nickName:String?,
    @ApiModelProperty(value = "닉네임 소개")
    var nickNameYn: CodeYn,
    @ApiModelProperty(value = "정보공개여부")
    var publicYn: CodeYn,
    @ApiModelProperty(value = "첨부 파일")
    var profileImg: MultipartFile?
)