package com.example.meufii.views.activity;

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.meufii.Interface.AtivoDao

@Database(version = 1, entities = arrayOf(Ativo::class))
abstract class AppDataBase : RoomDatabase() {
    abstract fun ativoDao(): AtivoDao
}
