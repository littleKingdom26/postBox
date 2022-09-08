package kr.co.postbox.entity.member

import kr.co.postbox.entity.BaseTimeEntity
import kr.co.postbox.entity.file.TbPostBoxFile
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
               var public:String,
               var marketingAgree: String,
               var heart:String?,
               var memberPassword:String,
               var role:String,
               @OneToOne(fetch = FetchType.LAZY)
               @JoinColumn(name="POST_BOX_FILE_KEY")
               var profileImg:TbPostBoxFile?) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var memberKey: Long?=null

}