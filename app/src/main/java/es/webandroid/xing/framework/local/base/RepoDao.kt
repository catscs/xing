package es.webandroid.xing.framework.local.base

import androidx.room.*
import es.webandroid.xing.framework.local.model.RepoModel

@Dao
interface RepoDao {

    @Query("SELECT * FROM RepoModel")
    fun getAll(): List<RepoModel>

    @Query("SELECT COUNT(id) FROM RepoModel")
    fun repoCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(venues: List<RepoModel>)

    @Query("DELETE FROM RepoModel")
    fun clearData()
}
