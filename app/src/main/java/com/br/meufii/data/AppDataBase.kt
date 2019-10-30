package com.br.meufii.data;

import androidx.room.Database
import androidx.room.RoomDatabase
import com.br.meufii.interfaceApp.AtivoDao
import com.br.meufii.interfaceApp.OperacaoDao
import com.br.meufii.model.Ativo
import com.br.meufii.model.Operacao

@Database(version = 1, entities = [Operacao::class, Ativo::class])
abstract class AppDataBase : RoomDatabase() {

    abstract fun operacaoDao(): OperacaoDao

    abstract fun ativoDao(): AtivoDao
}
