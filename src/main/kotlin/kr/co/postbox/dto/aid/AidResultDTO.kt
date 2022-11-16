package kr.co.postbox.dto.aid

import kr.co.postbox.dto.member.MemberResultDTO
import kr.co.postbox.entity.aid.TbAid

data class AidResultDTO(
        var member:MemberResultDTO,
        var aidKey:Long?
    )
{
    constructor(tbAid:TbAid):this(
    member = MemberResultDTO(tbAid.member),
    aidKey = tbAid.aidKey

    )
}


