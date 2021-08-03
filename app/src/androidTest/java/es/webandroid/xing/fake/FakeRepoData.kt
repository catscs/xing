package es.webandroid.xing.fake

import es.webandroid.xing.domain.entities.Repo

object FakeRepoData {
    val repos = listOf(
        Repo(1, "name1", "login1", "description1", "avatar1", "https://github.com/google/dagger", false),
        Repo(2, "name2", "login2", "description2", "avatar2", "https://github.com/google/dagger", true),
        Repo(3, "name3", "login3", "description3", "avatar3", "https://github.com/google/dagger", false),
        Repo(4, "name4", "login4", "description4", "avatar4", "https://github.com/google/dagger", true),
        Repo(5, "name5", "login5", "description5", "avatar5", "https://github.com/google/dagger", false),
    )
}
