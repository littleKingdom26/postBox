package kr.co.postbox.repository.selection

import kr.co.postbox.entity.selection.TbSelection
import org.springframework.data.jpa.repository.JpaRepository

interface SelectionRepository :JpaRepository<TbSelection,Long> {
}