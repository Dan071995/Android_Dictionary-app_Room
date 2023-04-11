package com.example.room_sql

import android.app.Application
import androidx.room.Room

class App:Application() {

    lateinit var dataBase:AppDataBase

    override fun onCreate() {
        super.onCreate()
        //Создаем экзкмпляр БД
        dataBase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "TestBD"
        ).build()
    }

}