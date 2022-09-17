package kr.co.postbox.service.reqeust

import kr.co.postbox.code.Path
import kr.co.postbox.config.exception.PostBoxException
import kr.co.postbox.dto.aid.AidResultDTO
import kr.co.postbox.dto.authUser.AuthUserDTO
import kr.co.postbox.dto.request.*
import kr.co.postbox.entity.aid.TbAid
import kr.co.postbox.entity.request.TbRequest
import kr.co.postbox.entity.request.TbRequestFile
import kr.co.postbox.repository.aid.AidRepository
import kr.co.postbox.repository.member.MemberRepository
import kr.co.postbox.repository.request.RequestFileRepository
import kr.co.postbox.repository.request.RequestRepository
import kr.co.postbox.utils.delete
import kr.co.postbox.utils.save
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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

    @set:Autowired
    lateinit var memberRepository: MemberRepository

    @set:Autowired
    lateinit var aidRepository: AidRepository

    @Value("\${file.upload.path}")
    lateinit var root: String

    /**
    * 의뢰 등록
     */
    @Transactional
    fun save(requestSaveDTO: RequestSaveDTO, authUserDTO: AuthUserDTO) : RequestResultDTO {

        val member = memberRepository.findById(authUserDTO.memberKey).orElseThrow { throw PostBoxException("MEMBER.NOT_FOUND") }

        val tbRequest = TbRequest(
            requestSaveDTO.title,
            requestSaveDTO.category.name,
            requestSaveDTO.sex.name,
            requestSaveDTO.detail ?: "",
            requestSaveDTO.negotiationYn.name,
            requestSaveDTO.price ?: -1,
            null,
            null,
            null,
            member
        )
        val requestSaveEntity = requestRepository.save(tbRequest)

        val fileResultList = mutableListOf<TbRequestFile>()
        try{
            saveRequestFile(requestSaveDTO.requestFileList, requestSaveEntity, fileResultList)
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
            saveRequestFile(requestUpdateDTO.requestFileList, request, fileResultList)
        }catch (e:Exception){
            throw PostBoxException("REQUEST.UPDATE.ERROR")
        }
        return RequestResultDTO(request)
    }

    /**
    * 의뢰 파일 저장
     */
    private fun saveRequestFile(requestFileList: List<MultipartFile>?, request: TbRequest?, fileResultList: MutableList<TbRequestFile>) {
        requestFileList?.forEach { multipartFile ->
            val fileResultDTO = multipartFile.save(Path.REQUEST.path, root)
            val save = requestFileRepository.save(
                TbRequestFile(
                    multipartFile.originalFilename ?: "",
                    fileResultDTO.fileName,
                    Path.REQUEST.name,
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
    fun deleteRequestFile(authUserDTO: AuthUserDTO, requestKey: Long, requestFileKey: Long) {
        val requestFile = requestFileRepository.findByRequestFileKeyAndRequest_RequestKeyAndRegId(requestFileKey, requestKey, authUserDTO.phoneNumber).orElseThrow { throw PostBoxException("REQUEST.FILE.NOT_FOUND") }
        requestFile?.delete(root)
        requestFileRepository.delete(requestFile)
    }

    /**
      *   의뢰 목록 조회
     */
    fun findByPage(requestSearchDTO: RequestSearchDTO): Page<RequestPageResultDTO> {
        val pageRequest = PageRequest.of(requestSearchDTO.currentPage, requestSearchDTO.pageSize, Sort.by("requestKey").descending())
        val keyword = requestSearchDTO.searchKeyword?:""
        return requestRepository.findByTitleContainsOrDetailContainsOrMember_NameContainsOrMember_NickNameContainsOrMember_DongContains(
            keyword, keyword, keyword, keyword, keyword, pageRequest
        ).map { RequestPageResultDTO(it) }
    }

    /**
    * 의뢰 삭제
     */
    fun deleteRequest(requestKey: Long, authUserDTO: AuthUserDTO) {
        val request = requestRepository.findById(requestKey).orElseThrow { throw PostBoxException("REQUEST.NOT_FOUND") }
        if (request.regId != authUserDTO.phoneNumber) {
            throw PostBoxException("REQUEST.NOT_FOUND")
        }
        try{
            val requestFileList = request.requestFileList

            requestFileList?.forEach {
                it.delete(root)
                requestFileRepository.delete(it)
            }
            requestRepository.delete(request)
        } catch (e:Exception){
            e.printStackTrace()
            throw PostBoxException("REQUEST.DELETE.ERROR")
        }
    }

    /**
    * 의뢰 신청
     */
    fun applyAid(requestKey: Long, authUserDTO: AuthUserDTO) : AidResultDTO {
        val request = requestRepository.findById(requestKey).orElseThrow { throw PostBoxException("REQUEST.NOT_FOUND") }
        if (request.member.memberKey==authUserDTO.memberKey) {
            throw PostBoxException("REQUEST.APPLY.SELF")
        }

        val member = memberRepository.findById(authUserDTO.memberKey).get()
        return AidResultDTO(aidRepository.save(TbAid(member, request)))
    }
}