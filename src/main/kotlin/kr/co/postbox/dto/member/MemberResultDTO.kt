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
var public:String) {
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
    tbMember.public
    )

    fun getMemberName():String{
        return if(public == CodeYn.Y.name){
            return name
        }else{
            return nickName?:""
        }
    }
}
