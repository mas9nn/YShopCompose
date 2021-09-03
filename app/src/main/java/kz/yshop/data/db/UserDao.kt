package kz.yshop.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.yshop.data.remote.responses.Demo.Data


@Dao
interface UserDao {

    @Query("SELECT * FROM USER")
    fun getUser(): LiveData<List<Data>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: Data)

    @Query("DELETE FROM USER")
    fun clear()
}