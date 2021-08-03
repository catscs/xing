package es.webandroid.xing.framework.local.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.webandroid.xing.framework.local.model.RepoModel

@Database(entities = [RepoModel::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun RepoDao(): RepoDao

    companion object {
        fun build(context: Context) = Room.databaseBuilder(context, RepoDatabase::class.java, "repo_db").build()

    }
}
