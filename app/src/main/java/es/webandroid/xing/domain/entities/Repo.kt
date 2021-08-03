package es.webandroid.xing.domain.entities

data class Repo(
    val id: Long,
    val name: String,
    val login: String,
    val description: String,
    val avatar: String,
    val urlRepo: String,
    var fork: Boolean
)


