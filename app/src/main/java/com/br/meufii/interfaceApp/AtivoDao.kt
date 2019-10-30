package com.br.meufii.interfaceApp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.br.meufii.model.CardResumoAtivo
import com.br.meufii.model.Ativo
import com.br.meufii.model.HomeResumoAtivo

@Dao
interface AtivoDao {
    @Query("SELECT * FROM ativo WHERE uuid = :uuidAtivo") fun getAtivo(uuidAtivo: String) : Ativo

    @Query("SELECT * FROM ativo") fun getAllAtivos() : List<Ativo>

    @Query("SELECT ativo.uuid, ativo.nome, ativo.codigo, SUM(operacao.valorCota * operacao.quantidadeCotas) AS valorInvestido, SUM(operacao.quantidadeCotas) as quantidadeCotas, SUM(0) as valorRendimento FROM ativo LEFT JOIN operacao on operacao.uuidAtivo = ativo.uuid WHERE quantidadeCotas > 0 GROUP BY ativo.uuid order by valorInvestido desc") fun getAllCardResumoAtivos() : List<CardResumoAtivo>

    @Query("SELECT SUM(operacao.valorCota * operacao.quantidadeCotas) AS valorInvestido, SUM(0) as valorRendimento FROM ativo INNER JOIN operacao on operacao.uuidAtivo = ativo.uuid") fun getResumoAtivos() : HomeResumoAtivo

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertAtivo(vararg ativo: Ativo)

    @Update fun updateAtivo(ativo: Ativo)

    @Delete fun deleteAtivo(ativo: Ativo)
}