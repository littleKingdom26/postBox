package kr.co.postbox.dto.file

data class FileResultDTO(
    var fileName: String,
    var filePath: String,
    var fileSize: Long?,
    val fileFullPath: String
)
