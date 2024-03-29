package kr.co.postbox.dto.member

import kr.co.postbox.code.CodeYn
import kr.co.postbox.code.Sex
import kr.co.postbox.entity.member.TbMember

data class MemberResultDTO(
    var phoneNumber:String,
    var name:String,
    var age:String,
    var sex:String,
    var address:String?,
    var si:String?,
    var dong:String?,
    var introduce:String?,
    var nickName:String?,
    var nickNameYn:String,
    var publicYn:String,
    var memberKey:Long?,
    val heart:Long?,
    var memberProfile: MemberProfileResultDTO?
) {
    constructor(tbMember: TbMember) : this(
        phoneNumber = tbMember.phoneNumber,
        name = tbMember.name,
        age = tbMember.age.toString(),
        sex = Sex.valueOf(tbMember.sex).codeName ,
        address = tbMember.address,
        si = tbMember.si,
        dong = tbMember.dong,
        introduce = tbMember.introduce,
        nickName = tbMember.nickName,
        nickNameYn = tbMember.nickNameYn,
        publicYn = tbMember.publicYn,
        memberKey = tbMember.memberKey,
        heart = tbMember.heart,
        memberProfile = tbMember.profileImg?.let { MemberProfileResultDTO(it) }
    )

    fun getMemberName(): String = when (nickNameYn) {
        CodeYn.N.name -> name
        else -> nickName ?: ""
    }

    fun getPublicAge(): String = when (publicYn) {
        CodeYn.Y.name -> age
        else -> "비공개"

    }


    fun getPublicSex(): String = when (publicYn) {
        CodeYn.Y.name -> sex
        else -> "비공개"
    }


    fun getPublicAddress(): String = when (publicYn) {
        CodeYn.Y.name -> address ?: ""
        else -> "비공개"
    }


    fun getPublicSi():String = when (publicYn) {
            CodeYn.Y.name -> si ?: ""
            else -> "비공개"
        }


    fun getPublicDong(): String = when (publicYn) {
        CodeYn.Y.name -> dong ?: ""
        else -> "비공개"
    }

}
