package kr.co.postbox.repository.aid

import kr.co.postbox.entity.aid.TbAid
import org.springframework.data.jpa.repository.JpaRepository

interface AidRepository : JpaRepository<TbAid,Long> {
}