package kr.co.mfort.graphql

import kr.co.mfort.graphql.entity.CardEntity
import kr.co.mfort.graphql.entity.CardEntityRepository
import kr.co.mfort.graphql.entity.UserEntity
import kr.co.mfort.graphql.entity.UserEntityRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphqlWorkshopApplication

fun main(args: Array<String>) {
    val ac = runApplication<GraphqlWorkshopApplication>(*args)

    val userEntityRepository = ac.getBean(UserEntityRepository::class.java)
    userEntityRepository.saveAll((1..5).map { i -> UserEntity(name = "유저$i") })

    val cardEntityRepository = ac.getBean(CardEntityRepository::class.java)
    cardEntityRepository.saveAll(
        listOf(
            CardEntity(userId = 1, number = "111-111-111", company = "BC"),
            CardEntity(userId = 2, number = "222-222-222", company = "SAMSUNG"),
            CardEntity(userId = 3, number = "333-333-333", company = "SINHAN"),
        )
    )
}
