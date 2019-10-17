package com.example.meufii.Interface

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.meufii.views.activity.Ativo

@Dao
interface AtivoDao {
    @Query("SELECT * FROM ativo") fun getAllAtivos() : List<Ativo>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertAtivo(vararg ativo: Ativo)

    @Update fun updateAtivo(ativo: Ativo)

    @Delete fun deleteAtivo(ativo: Ativo)
}