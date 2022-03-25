package kr.co.mfort.graphql

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import kr.co.mfort.graphql.schema.CardSchema
import kr.co.mfort.graphql.schema.UserSchema

@DgsComponent
class QueryDataFetcher {
    @DgsQuery(field = "users")
    fun users(): List<UserSchema> = listOf(
        UserSchema(id = 1L, name = "유저1"),
        UserSchema(id = 2L, name = "유저2"),
        UserSchema(id = 3L, name = "유저3"),
        UserSchema(id = 4L, name = "유저4"),
        UserSchema(id = 5L, name = "유저5"),
    )

    @DgsQuery(field = "cards")
    fun cards(): List<CardSchema> {
        return listOf(
            CardSchema(id = 1L, number = "111-111-111", company = "BC"),
            CardSchema(id = 2L, number = "222-222-222", company = "SAMSUNG"),
            CardSchema(id = 3L, number = "333-333-333", company = "SINHAN"),
        )
    }
}
