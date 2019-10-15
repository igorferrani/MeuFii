package com.example.meufii

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ativo")
data class Ativo(var nome: String, var codigo: String, @PrimaryKey val uuid: String = UUID.randomUUID().toString())
