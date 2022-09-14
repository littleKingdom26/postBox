package kr.co.postbox.repository.request

import kr.co.postbox.entity.request.TbRequestFile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RequestFileRepository : JpaRepository<TbRequestFile, Long> {


    fun findByRequestFileKeyAndRequest_RequestKeyAndRegId(requestFileKey: Long, requestKey: Long, regId: String): Optional<TbRequestFile>

}