package es.webandroid.xing.data.model

data class RepoDataModel(
    val id: Long,
    val name: String,
    val login: String,
    val description: String,
    val avatar: String,
    val urlRepo: String,
    var fork: Boolean
) {
    companion object {
        val empty = RepoDataModel(0, "", "","","","", false)
    }
}
