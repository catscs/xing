package es.webandroid.xing.framework.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepoModel(
    @PrimaryKey
    val id: Long,
    val name: String,
    val login: String,
    val description: String,
    val urlRepo: String,
    val avatar: String,
    var fork: Boolean
)
