package kr.co.mfort.graphql.entity

import javax.persistence.*

@Entity
class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        private set

    @Column
    val userId: Long

    @Column
    var number: String
        private set

    @Column
    var company: String
        private set

    constructor(userId: Long, number: String, company: String) {
        this.userId = userId
        this.number = number
        this.company = company
    }
}
