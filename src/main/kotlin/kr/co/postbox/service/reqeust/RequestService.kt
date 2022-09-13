package kr.co.postbox.service.reqeust

import kr.co.postbox.code.Path
import kr.co.postbox.dto.request.RequestResultDTO
import kr.co.postbox.dto.request.RequestSaveDTO
import kr.co.postbox.entity.request.TbRequest
import kr.co.postbox.entity.request.TbRequestFile
import kr.co.postbox.repository.request.ReqeustFileRepository
import kr.co.postbox.repository.request.RequestRepository
import kr.co.postbox.utils.save
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class RequestService {

    @set:Autowired
    lateinit var requestRepository: RequestRepository

    @set:Autowired
    lateinit var requestFileRepository: ReqeustFileRepository


    @Value("\${file.upload.path}")
    lateinit var root: String

    fun save(requestSaveDTO: RequestSaveDTO) : RequestResultDTO {




        val fileResultList = mutableListOf<TbRequestFile>()
        requestSaveDTO.requestFileList?.forEach { multipartFile ->
            val fileResultDTO = multipartFile.save(Path.REQUEST.name, root)
            val save = requestFileRepository.save(
                TbRequestFile(
                    multipartFile.originalFilename ?: "",
                    fileResultDTO.fileName,
                    fileResultDTO.filePath,
                    fileResultDTO.fileSize ?: 0, null
                )
            )
            fileResultList.add(save)
        }

        val tbRequest = TbRequest(
            requestSaveDTO.title,
            requestSaveDTO.category.codeName,
            requestSaveDTO.sex.codeName,
            requestSaveDTO.detail ?: "",
            requestSaveDTO.negotiationYn.name,
            requestSaveDTO.price ?: -1,
            null,
            fileResultList
        )

        return RequestResultDTO(requestRepository.save(tbRequest))
    }
}