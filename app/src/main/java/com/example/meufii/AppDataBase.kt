package com.example.meufii;

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = arrayOf(Ativo::class))
abstract class AppDataBase : RoomDatabase() {
    abstract fun ativoDao(): AtivoDao
}
