package kr.co.postbox.entity.member

import kr.co.postbox.dto.member.MemberUpdateDTO
import kr.co.postbox.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="TB_MEMBER")
class TbMember(var phoneNumber:String,
               var name: String,
               var age: Long,
               var sex: String,
               var address: String?,
               var si: String?,
               var dong:String?,
               var introduce:String?,
               var nickName:String?,
               var nickNameYn:String,
               var publicYn:String,
               var marketingAgree: String,
               var heart:Long?,
               var memberPassword:String,
               var role:String,
               @OneToOne(fetch = FetchType.LAZY)
               @JoinColumn(name="MEMBER_FILE_KEY")
               var profileImg: TbMemberFile?) : BaseTimeEntity() {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var memberKey: Long?=null

    fun update(memberUpdateDTO: MemberUpdateDTO) {
        introduce = memberUpdateDTO.introduce
        nickName = memberUpdateDTO.nickName
        nickNameYn = memberUpdateDTO.nickNameYn.name
        publicYn = memberUpdateDTO.publicYn.name
    }
}