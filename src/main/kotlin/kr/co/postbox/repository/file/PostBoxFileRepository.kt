package kr.co.postbox.repository.file

import kr.co.postbox.entity.file.TbPostBoxFile
import org.springframework.data.jpa.repository.JpaRepository

interface PostBoxFileRepository : JpaRepository<TbPostBoxFile, Long> {
}