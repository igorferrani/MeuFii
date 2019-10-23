package com.example.meufii.interfaceApp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.meufii.model.Operacao

@Dao
interface OperacaoDao {
    @Query("SELECT * FROM operacao") fun getAllOperacoes() : List<Operacao>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertOperacao(vararg operacao: Operacao)

    @Update fun updateOperacao(operacao: Operacao)

    @Delete fun deleteOperacao(operacao: Operacao)
}