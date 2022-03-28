package kr.co.mfort.graphql.entity

import javax.persistence.*

@Entity
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        private set

    @Column
    var name: String
        private set

    constructor(name: String) {
        this.name = name
    }
}
