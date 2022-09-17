package kr.co.postbox.entity.aid

import kr.co.postbox.entity.BaseTimeEntity
import kr.co.postbox.entity.member.TbMember
import kr.co.postbox.entity.request.TbRequest
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*


@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="TB_AID")
class TbAid(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_KEY")
    var member: TbMember,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUEST_KEY")
    var request: TbRequest,
    ):BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var aidKey: Long? = null
}