package kr.co.postbox.repository.request

import kr.co.postbox.entity.request.TbRequest
import org.springframework.data.jpa.repository.JpaRepository

interface RequestRepository : JpaRepository<TbRequest, Long> {

}