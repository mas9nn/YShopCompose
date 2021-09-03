package kz.yshop.data.remote.responses.Demo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "User")
data class Data(
    val hash: String,
    @PrimaryKey
    val id: Int
)