package kr.co.postbox.repository.request

import kr.co.postbox.entity.request.TbRequestFile
import org.springframework.data.jpa.repository.JpaRepository

interface ReqeustFileRepository : JpaRepository<TbRequestFile, Long> {
}