package kr.co.postbox.entity.request

import kr.co.postbox.entity.BaseTimeEntity
import kr.co.postbox.entity.aid.TbAid
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
    var requestFileList:List<TbRequestFile>?=null
    ):BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var requestKey: Long? = null
}