package kr.co.postbox.entity.file

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
@Table(name="TB_POST_BOX_FILE")
class TbPostBoxFile(
                var orignalFIleName:String,
                var fileName:String,
                var filePath:String,
                var fileSize:Long) :BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var postBoxFileKey: Long? = null
}