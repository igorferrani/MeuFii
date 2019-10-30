package com.br.meufii.interfaceApp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.br.meufii.model.Operacao

@Dao
interface OperacaoDao {
    @Query("SELECT * FROM operacao WHERE uuidAtivo = :uuidAtivo") fun getAllOperacoes(uuidAtivo: String) : List<Operacao>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertOperacao(vararg operacao: Operacao)

    @Update fun updateOperacao(operacao: Operacao)

    @Delete fun deleteOperacao(operacao: Operacao)
}