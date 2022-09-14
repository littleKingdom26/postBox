package kr.co.postbox.service.reqeust

import kr.co.postbox.code.Path
import kr.co.postbox.config.exception.PostBoxException
import kr.co.postbox.dto.authUser.AuthUserDTO
import kr.co.postbox.dto.request.RequestResultDTO
import kr.co.postbox.dto.request.RequestSaveDTO
import kr.co.postbox.dto.request.RequestUpdateDTO
import kr.co.postbox.entity.request.TbRequest
import kr.co.postbox.entity.request.TbRequestFile
import kr.co.postbox.repository.request.RequestFileRepository
import kr.co.postbox.repository.request.RequestRepository
import kr.co.postbox.utils.delete
import kr.co.postbox.utils.save
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class RequestService {

    private val log = LoggerFactory.getLogger(RequestService::class.java)

    @set:Autowired
    lateinit var requestRepository: RequestRepository

    @set:Autowired
    lateinit var requestFileRepository: RequestFileRepository


    @Value("\${file.upload.path}")
    lateinit var root: String

    @Transactional
    fun save(requestSaveDTO: RequestSaveDTO) : RequestResultDTO {

        val tbRequest = TbRequest(
            requestSaveDTO.title,
            requestSaveDTO.category.name,
            requestSaveDTO.sex.name,
            requestSaveDTO.detail ?: "",
            requestSaveDTO.negotiationYn.name,
            requestSaveDTO.price ?: -1,
            null,
            null
        )
        val requestSaveEntity = requestRepository.save(tbRequest)

        val fileResultList = mutableListOf<TbRequestFile>()
        try{
            requestFileSave(requestSaveDTO.requestFileList, requestSaveEntity, fileResultList)
        }catch (e:Exception){
            throw PostBoxException("REQUEST.SAVE.ERROR")
        }
        requestSaveEntity.requestFileList=fileResultList

        return RequestResultDTO(requestSaveEntity)
    }

    /*
    의뢰 수정
     */
    @Transactional
    fun update(requestUpdateDTO: RequestUpdateDTO,authUserDTO: AuthUserDTO):RequestResultDTO {
        val findById = requestRepository.findById(requestUpdateDTO.requestKey)

        val request = findById.orElseThrow { throw PostBoxException("REQUEST.NOT_FOUND") }
        if (request.regId != authUserDTO.phoneNumber) {
            throw PostBoxException("REQUEST.NOT_FOUND")
        }
        request.update(requestUpdateDTO)
        val fileResultList = mutableListOf<TbRequestFile>()
        try{
            requestFileSave(requestUpdateDTO.requestFileList, request, fileResultList)
        }catch (e:Exception){
            throw PostBoxException("REQUEST.UPDATE.ERROR")
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

    /**
     * 의뢰 조회
     */

    fun findByRequest(requestKey: Long) :RequestResultDTO {
        return RequestResultDTO(requestRepository.findById(requestKey).orElseThrow { throw PostBoxException("REQUEST.NOT_FOUND") })

    }

    /**
     * 의뢰 파일 삭제
     */
    @Transactional
    fun requestFileDelete(authUserDTO: AuthUserDTO,requestKey: Long, requestFileKey: Long) {
        val requestFile = requestFileRepository.findByRequestFileKeyAndRequest_RequestKeyAndRegId(requestFileKey, requestKey, authUserDTO.phoneNumber).orElseThrow { throw PostBoxException("REQUEST.FILE.NOT_FOUND") }
        try{
            requestFile.delete(root)
        }catch (e:Exception){
            e.printStackTrace()
            log.debug("파일없음")
        }
        requestFileRepository.delete(requestFile)

    }
}