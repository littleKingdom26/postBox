package kr.co.postbox.entity.member

import kr.co.postbox.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="TB_MEMBER_FILE")
class TbMemberFile(
    var originalFIleName:String,
    var fileName:String,
    var filePath:String,
    var fileSize:Long) :BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var memberFileKey: Long? = null
}