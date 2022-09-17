package kr.co.postbox.dto.member

import kr.co.postbox.code.Path
import kr.co.postbox.entity.member.TbMemberFile

data class MemberProfileResultDTO(
    var postBoxFileKey: Long?,
    var originalFileName: String,
    var fileName: String,
    var filePath: String,
    var fileSize: Long)
{
    constructor(tbMemberFile: TbMemberFile) : this(
        postBoxFileKey = tbMemberFile.memberFileKey,
        originalFileName = tbMemberFile.originalFIleName,
        fileName = tbMemberFile.fileName,
        filePath = tbMemberFile.filePath,
        fileSize = tbMemberFile.fileSize
    )

    fun getProfileImgUrl(): String = "/imageView/" + Path.valueOf(filePath).path + "/" + fileName

}
