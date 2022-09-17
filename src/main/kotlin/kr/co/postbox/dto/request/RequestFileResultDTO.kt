package kr.co.postbox.dto.request

import kr.co.postbox.code.Path
import kr.co.postbox.entity.request.TbRequestFile

data class RequestFileResultDTO(
    var requestFileKey:Long?,
    var originalFileName: String,
    var fileName: String,
    var filePath: String,
    var fileSize: Long) {
    constructor(tbRequestFile: TbRequestFile) : this(
        requestFileKey = tbRequestFile.requestFileKey,
        originalFileName =  tbRequestFile.originalFileName,
        fileName = tbRequestFile.fileName,
        filePath = tbRequestFile.filePath,
        fileSize = tbRequestFile.fileSize)

    fun getRequestImgUrl(): String = "/imageView/" + Path.valueOf(filePath).path + "/" + fileName
}
