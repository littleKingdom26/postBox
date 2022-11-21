package kr.co.postbox.utils

import kr.co.postbox.dto.file.FileResultDTO
import kr.co.postbox.entity.member.TbMemberFile
import kr.co.postbox.entity.request.TbRequestFile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * multipartFile 을 물리 저장
 */
fun MultipartFile?.save(subFolder: String?,root:String): FileResultDTO {
    val newFileName = makeFileName(this)
    val newFilePath = makeCreateDirectory(subFolder ?: "default", newFileName, root)
    val newFile = File(newFilePath)
    // 용량 줄이기
    newFile.createNewFile()
    this?.transferTo(newFile)
    return FileResultDTO(newFileName, subFolder?: "default", this?.size, newFilePath)
}

fun TbRequestFile.delete(root:String){
    val filePath = this.filePath
    val fileName = this.fileName
    val file = File(root + File.separator + filePath + File.separator + fileName)
    file.isFile.takeIf { true }?.apply { file.delete() }
}

fun TbMemberFile.delete(root: String) {
    val filePath = this.filePath
    val fileName = this.fileName
    val file = File(root + File.separator + filePath + File.separator + fileName)
    takeIf { file.isFile }?.let { file.delete() }
}

fun String?.encodePassword(passwordEncoder: PasswordEncoder):String?{
    val encode = passwordEncoder.encode(this)
    return if (encode.isNotEmpty()) {
        encode
    }else{
        null
    }
}

/**
 * 새로운 파일 생성
 */
fun makeFileName(file: MultipartFile?): String {
    val extension = "." + StringUtils.getFilenameExtension(file?.originalFilename)
    val dtf = DateTimeFormatter.ofPattern("uuuuMMddHHmmssSSS")
    return LocalDateTime.now().format(dtf) + extension
}

/**
 * 폴더 생성
 */
fun makeCreateDirectory(subFolder: String?, fileName: String,root:String): String {
    val newPath: String = root + File.separator + subFolder
    val path = Paths.get(newPath)
    if (!Files.isDirectory(path)) {
        Files.createDirectories(path)
    }
    return newPath + File.separator + fileName
}
