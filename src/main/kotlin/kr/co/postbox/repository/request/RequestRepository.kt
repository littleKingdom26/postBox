package kr.co.postbox.repository.request

import kr.co.postbox.entity.request.TbRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface RequestRepository : JpaRepository<TbRequest, Long> {


    fun findByTitleContainsOrDetailContainsOrMember_NameContainsOrMember_NickNameContainsOrMember_DongContains(title: String, detail: String, name: String, nickName: String, dong: String, pageable: Pageable): Page<TbRequest>

}