package kr.co.postbox.service.reqeust

import kr.co.postbox.dto.request.RequestResultDTO
import kr.co.postbox.dto.request.RequestSaveDTO
import kr.co.postbox.entity.request.TbRequest
import kr.co.postbox.repository.request.RequestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RequestService {

    @set:Autowired
    lateinit var requestRepository: RequestRepository

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

        return RequestResultDTO(requestRepository.save(tbRequest))
    }
}