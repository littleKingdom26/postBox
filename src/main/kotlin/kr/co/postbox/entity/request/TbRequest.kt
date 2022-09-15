package kr.co.postbox.entity.request

import kr.co.postbox.dto.request.RequestUpdateDTO
import kr.co.postbox.entity.BaseTimeEntity
import kr.co.postbox.entity.aid.TbAid
import kr.co.postbox.entity.member.TbMember
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*


@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="TB_REQUEST")
class TbRequest(
    var title:String,
    var category:String,
    var sex:String,
    var detail:String?,
    var negotiationYn:String,
    var price:Long,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "tbRequest")
    var aidList:List<TbAid>?=null,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "request")
    var requestFileList:List<TbRequestFile>?=null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_KEY")
    var member:TbMember
    ):BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var requestKey: Long? = null

    fun update(requestUpdateDTO: RequestUpdateDTO) {
        this.title = requestUpdateDTO.title
        this.category = requestUpdateDTO.category.name
        this.sex = requestUpdateDTO.sex.name
        this.detail = requestUpdateDTO.detail?:this.detail
        this.negotiationYn = requestUpdateDTO.negotiationYn.name
        this.price = requestUpdateDTO.price?:this.price
    }
}