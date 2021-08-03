package es.webandroid.xing.framework.remote.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoResponseDataJson (
    val id: Long,
    val name: String,
    val owner: Owner,
    val description: String?,
    val html_url: String,
    val fork: Boolean
) {
    companion object {
        val empty = RepoResponseDataJson(0, "", Owner("", ""), "", "", false)
    }
}

@JsonClass(generateAdapter = true)
data class Owner (
    val login: String,
    val avatar_url: String
)
