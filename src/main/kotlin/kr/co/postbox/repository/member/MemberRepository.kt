package kr.co.postbox.repository.member

import kr.co.postbox.entity.member.TbMember
import org.springframework.data.jpa.repository.JpaRepository


interface MemberRepository: JpaRepository<TbMember,Long>{

    fun findByPhoneNumber(phoneNumber:String): TbMember?


    fun countByPhoneNumber(phoneNumber: String): Long


}
