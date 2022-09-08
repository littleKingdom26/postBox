package kr.co.postbox.dto.member

import io.swagger.annotations.ApiModelProperty
import kr.co.postbox.code.CodeYn
import kr.co.postbox.code.Sex
import org.springframework.web.multipart.MultipartFile

data class MemberSaveDTO (
    @ApiModelProperty(value = "이름", required = true)
    var name:String,
    @ApiModelProperty(value = "전화번호", required = true)
    var phoneNumber:String,
    @ApiModelProperty(value = "나이", required = true)
    var age: Long,
    @ApiModelProperty(value = "성별", required = true)
    var sex: Sex,
    @ApiModelProperty(value = "주소")
    var address: String,
    @ApiModelProperty(value = "시/구군")
    var si : String?,
    @ApiModelProperty(value = "동")
    var dong: String?,
    @ApiModelProperty(value = "자기소개")
    var introduce:String?,
    @ApiModelProperty(value = "닉네임")
    var nickName:String?,
    @ApiModelProperty(value = "정보공개여부")
    var public:CodeYn,
    @ApiModelProperty(value = "마케팅 수신 여부")
    var marketingAgree: CodeYn,
    @ApiModelProperty(value = "첨부 파일")
    var profileImg: MultipartFile?
)