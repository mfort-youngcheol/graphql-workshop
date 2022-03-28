package kr.co.mfort.graphql

import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.graphql.dgs.DgsQueryExecutor
import kr.co.mfort.graphql.entity.CardEntity
import kr.co.mfort.graphql.entity.CardEntityRepository
import kr.co.mfort.graphql.entity.UserEntity
import kr.co.mfort.graphql.entity.UserEntityRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QueryDataFetcherTest {
    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @Autowired
    private lateinit var cardEntityRepository: CardEntityRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var dgsQueryExecutor: DgsQueryExecutor

    @BeforeAll
    fun initData() {
        userEntityRepository.saveAll((1..5).map { i -> UserEntity(name = "유저$i") })

        cardEntityRepository.saveAll(
            listOf(
                CardEntity(userId = 1, number = "111-111-111", company = "BC"),
                CardEntity(userId = 2, number = "222-222-222", company = "SAMSUNG"),
                CardEntity(userId = 3, number = "333-333-333", company = "SINHAN"),
            )
        )
    }

    @Test
    fun usingMockMvc() {
        val result = mockMvc.post("/graphql") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "query": "{ users { id name card { id number company } } }" }"""
        }.andReturn().response

        val responseBody = ObjectMapper().readValue(result.contentAsString, Map::class.java)
        val data = responseBody["data"] as Map<String, *>
        assertThat(data["users"] as List<*>).hasSize(5)
    }

    @Test
    fun usingDgsQueryExecutor() {
        val result = dgsQueryExecutor.execute("{ users { id name card { id number company } } }")
        val data = result.getData<Map<String, *>>()
        assertThat(data["users"] as List<*>).hasSize(5)
    }
}