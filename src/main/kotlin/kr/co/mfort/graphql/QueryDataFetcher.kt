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
    private val cardEntityRepository: CardEntityRepository,
) {
    @DgsQuery(field = "users")
    fun users(): List<UserSchema> =
        this.userEntityRepository.findAll()
            .map { UserSchema(id = it.id, name = it.name) }

    @DgsData(parentType = "User", field = "card")
    fun userCard(dfe: DgsDataFetchingEnvironment): CardSchema? {
        val user = dfe.getSource<UserSchema>()
        return this.cardEntityRepository.findByUserId(user.id)
    }

    @DgsQuery(field = "cards")
    fun cards(): List<CardSchema> =
        this.cardEntityRepository.findAll()
            .map { CardSchema(id = it.id, number = it.number, company = it.company) }
}
