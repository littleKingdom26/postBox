package kr.co.postbox.dto.selection

import kr.co.postbox.dto.member.MemberResultDTO
import kr.co.postbox.entity.selection.TbSelection

data class SelectionResultDTO(
    var selectionKey: Long?,
    var member: MemberResultDTO
){
    constructor(tbSelection: TbSelection) : this(
    selectionKey = tbSelection.selectionKey,
    member = MemberResultDTO(tbSelection.member)
    )

}
