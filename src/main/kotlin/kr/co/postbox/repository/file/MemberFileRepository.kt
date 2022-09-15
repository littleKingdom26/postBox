package kr.co.postbox.repository.file

import kr.co.postbox.entity.member.TbMemberFile
import org.springframework.data.jpa.repository.JpaRepository

interface MemberFileRepository : JpaRepository<TbMemberFile, Long> {
}