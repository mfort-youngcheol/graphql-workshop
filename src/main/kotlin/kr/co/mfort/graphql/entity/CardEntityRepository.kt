package kr.co.mfort.graphql.entity

import org.springframework.data.jpa.repository.JpaRepository

interface CardEntityRepository : JpaRepository<CardEntity, Long>
