package kr.co.postbox.entity.request

import kr.co.postbox.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*


@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="TB_REQUEST_FILE")
class TbRequestFile(
        var originalFileName:String,
        var fileName:String,
        var filePath:String,
        var fileSize:Long,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="REQUEST_KEY")
        var request: TbRequest?=null
        ):BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var requestFileKey: Long? = null

}