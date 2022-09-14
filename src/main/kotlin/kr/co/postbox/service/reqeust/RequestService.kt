package kr.co.postbox.service.reqeust

import kr.co.postbox.code.Path
import kr.co.postbox.config.exception.PostBoxException
import kr.co.postbox.dto.authUser.AuthUserDTO
import kr.co.postbox.dto.request.RequestResultDTO
import kr.co.postbox.dto.request.RequestSaveDTO
import kr.co.postbox.dto.request.RequestUpdateDTO
import kr.co.postbox.entity.request.TbRequest
import kr.co.postbox.entity.request.TbRequestFile
import kr.co.postbox.repository.request.ReqeustFileRepository
import kr.co.postbox.repository.request.RequestRepository
import kr.co.postbox.utils.save
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class RequestService {

    @set:Autowired
    lateinit var requestRepository: RequestRepository

    @set:Autowired
    lateinit var requestFileRepository: ReqeustFileRepository


    @Value("\${file.upload.path}")
    lateinit var root: String

    fun save(requestSaveDTO: RequestSaveDTO) : RequestResultDTO {

        val tbRequest = TbRequest(
            requestSaveDTO.title,
            requestSaveDTO.category.codeName,
            requestSaveDTO.sex.codeName,
            requestSaveDTO.detail ?: "",
            requestSaveDTO.negotiationYn.name,
            requestSaveDTO.price ?: -1,
            null,
            null
        )
        val requestSaveEntity = requestRepository.save(tbRequest)

        val fileResultList = mutableListOf<TbRequestFile>()
        requestFileSave(requestSaveDTO.requestFileList, requestSaveEntity, fileResultList)
        requestSaveDTO.requestFileList?.forEach { multipartFile ->
            val fileResultDTO = multipartFile.save(Path.REQUEST.path, root)
            val save = requestFileRepository.save(
                TbRequestFile(
                    multipartFile.originalFilename ?: "",
                    fileResultDTO.fileName,
                    fileResultDTO.filePath,
                    fileResultDTO.fileSize ?: 0, requestSaveEntity
                )
            )
            fileResultList.add(save)
        }

        requestSaveEntity.requestFileList=fileResultList

        return RequestResultDTO(requestSaveEntity)
    }

    /*
    의뢰 수정
     */
    fun update(requestUpdateDTO: RequestUpdateDTO,authUserDTO: AuthUserDTO):RequestResultDTO {
        val findById = requestRepository.findById(requestUpdateDTO.requestKey)

        val request = findById.orElseThrow { throw PostBoxException("REQUEST.NOT_FOUND") }
        if (request.regId != authUserDTO.phoneNumber) {
            throw PostBoxException("REQUEST.NOT_FOUND")
        }
        request.update(requestUpdateDTO)
        val fileResultList = mutableListOf<TbRequestFile>()
        requestFileSave(requestUpdateDTO.requestFileList, request, fileResultList)
        if (fileResultList.isNotEmpty()) {
            request.requestFileList = request.requestFileList?.plus(fileResultList)
        }
        return RequestResultDTO(request)
    }

    private fun requestFileSave(requestFileList: List<MultipartFile>?, request: TbRequest?, fileResultList: MutableList<TbRequestFile>) {
        requestFileList?.forEach { multipartFile ->
            val fileResultDTO = multipartFile.save(Path.REQUEST.path, root)
            val save = requestFileRepository.save(
                TbRequestFile(
                    multipartFile.originalFilename ?: "",
                    fileResultDTO.fileName,
                    fileResultDTO.filePath,
                    fileResultDTO.fileSize ?: 0, request
                )
            )
            fileResultList.add(save)
        }
    }
}