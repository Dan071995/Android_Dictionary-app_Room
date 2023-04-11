package com.example.room_sql

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    //Метод получения 5-ти первых значений из БД
    @Query("SELECT * FROM word LIMIT 5")
    fun getWords():Flow<List<Word>>

    //Метод добавления слова в БД
    @Insert()
    suspend fun insertWord(word:Word)

    //В этом методе мы ПЫТАЕМСЯ получить введенную пользователем строку из БД
    //Если такого значения в БД нет, вернется null!
    @Query("SELECT userWord FROM word WHERE userWord LIKE :userWd")
    suspend fun tryToGetUserWord(userWd:String):String?

    @Query("UPDATE Word SET wordCounter = wordCounter + 1 WHERE userWord LIKE :userWord")
    suspend fun increaseWordCounter(userWord:String)

    //Метод по удалению БД
    @Query("DELETE FROM word")
    suspend fun clearDataBase()
}