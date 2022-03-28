package kr.co.mfort.graphql.entity

import kr.co.mfort.graphql.schema.CardSchema
import org.springframework.data.jpa.repository.JpaRepository

interface CardEntityRepository : JpaRepository<CardEntity, Long> {
    fun findByUserId(userId: Long): CardSchema?
}
