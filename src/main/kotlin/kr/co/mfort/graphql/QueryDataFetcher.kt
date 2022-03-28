package kr.co.mfort.graphql

import com.netflix.graphql.dgs.*
import kr.co.mfort.graphql.entity.CardEntity
import kr.co.mfort.graphql.entity.CardEntityRepository
import kr.co.mfort.graphql.entity.UserEntity
import kr.co.mfort.graphql.entity.UserEntityRepository
import kr.co.mfort.graphql.schema.CardSchema
import kr.co.mfort.graphql.schema.UserSchema

@DgsComponent
class MutationDataFetcher(
    private val userEntityRepository: UserEntityRepository,
    private val cardEntityRepository: CardEntityRepository,
) {
    @DgsMutation(field = "user")
    fun user(@InputArgument("name") name: String): UserSchema {
        val userEntity = this.userEntityRepository.save(UserEntity(name = name))
        return UserSchema(id = userEntity.id, name = userEntity.name)
    }

    @DgsMutation(field = "card")
    fun card(
        @InputArgument("userId") userId: Long,
        @InputArgument("number") number: String,
        @InputArgument("company") company: String
    ): CardSchema {
        val cardEntity = this.cardEntityRepository.save(
            CardEntity(userId = userId, number = number, company = company)
        )
        return CardSchema(id = cardEntity.id, number = cardEntity.number, company = cardEntity.company)
    }
}

@DgsComponent
class QueryDataFetcher(
    private val userEntityRepository: UserEntityRepository,
) {
    @DgsQuery(field = "users")
    fun users(): List<UserSchema> = listOf(
        UserSchema(id = 1L, name = "유저1"),
        UserSchema(id = 2L, name = "유저2"),
        UserSchema(id = 3L, name = "유저3"),
        UserSchema(id = 4L, name = "유저4"),
        UserSchema(id = 5L, name = "유저5"),
    )

    @DgsData(parentType = "User", field = "card")
    fun userCard(dfe: DgsDataFetchingEnvironment): CardSchema? {
        val user = dfe.getSource<UserSchema>()

        return when (user.id) {
            1L -> CardSchema(id = 1L, number = "111-111-111", company = "BC")
            2L -> CardSchema(id = 2L, number = "222-222-222", company = "SAMSUNG")
            3L -> CardSchema(id = 3L, number = "333-333-333", company = "SINHAN")
            else -> null
        }
    }

    @DgsQuery(field = "cards")
    fun cards(): List<CardSchema> {
        return listOf(
            CardSchema(id = 1L, number = "111-111-111", company = "BC"),
            CardSchema(id = 2L, number = "222-222-222", company = "SAMSUNG"),
            CardSchema(id = 3L, number = "333-333-333", company = "SINHAN"),
        )
    }
}
