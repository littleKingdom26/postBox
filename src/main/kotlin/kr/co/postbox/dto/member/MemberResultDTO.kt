package kr.co.postbox.dto.member

import kr.co.postbox.code.CodeYn
import kr.co.postbox.entity.member.TbMember

data class MemberResultDTO(
    var phoneNumber:String,
    var name:String,
    var age:Long,
    var sex:String,
    var address:String?,
    var si:String?,
    var dong:String?,
    var introduce:String?,
    var nickName:String?,
    var public:String,
    var memberKey:Long?,
    var profileImg: ProfileImgResultDTO?
) {
    constructor(tbMember: TbMember) : this(
        tbMember.phoneNumber,
        tbMember.name,
        tbMember.age,
        tbMember.sex,
        tbMember.address,
        tbMember.si,
        tbMember.dong,
        tbMember.introduce,
        tbMember.nickName,
        tbMember.public,
        tbMember.memberKey,
        tbMember.profileImg?.let { ProfileImgResultDTO(it) }
    )

    fun getMemberName():String{
        return when (public) {
            CodeYn.Y.name -> name
            else -> nickName ?: ""
        }
    }
}
