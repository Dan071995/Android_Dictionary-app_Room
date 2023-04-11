package com.example.room_sql

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "Word",
    //primaryKeys = ["id","userWord"]
)
data class Word(

    /*@ColumnInfo(name = "id")
    val id:Int,*/

    @PrimaryKey
    @ColumnInfo(name = "userWord")
    val userWord:String,

    @ColumnInfo(name = "wordCounter")
    val wordCounter:Int? = null
)
