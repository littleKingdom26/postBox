package kr.co.postbox.dto.member

import kr.co.postbox.entity.file.TbPostBoxFile

data class ProfileImgResultDTO(
    var postBoxFileKey: Long?,
    var originalFileName: String,
    var fileName: String,
    var filePath: String,
    var fileSize: Long)
{
    constructor(tbPostBoxFile: TbPostBoxFile) : this(
        postBoxFileKey = tbPostBoxFile.postBoxFileKey,
        originalFileName = tbPostBoxFile.orignalFIleName,
        fileName = tbPostBoxFile.fileName,
        filePath = tbPostBoxFile.filePath,
        fileSize = tbPostBoxFile.fileSize
    )

}
