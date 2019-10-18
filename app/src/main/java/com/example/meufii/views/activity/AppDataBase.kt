package com.example.meufii.views.activity;

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.meufii.Interface.OperacaoDao
import com.example.meufii.model.Operacao

@Database(version = 1, entities = arrayOf(Operacao::class))
abstract class AppDataBase : RoomDatabase() {
    abstract fun operacaoDao(): OperacaoDao
}
