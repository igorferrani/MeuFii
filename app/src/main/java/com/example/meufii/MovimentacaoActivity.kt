package com.example.meufii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import java.util.UUID.randomUUID



class MovimentacaoActivity : AppCompatActivity() {

    private lateinit var etNome: EditText
    private lateinit var etCodigo: EditText
    private lateinit var etDate: EditText
    private lateinit var btnRegistrar: Button

    companion object {
        var database: AppDataBase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimentacao)

        initView()
        initDb()
    }

    fun initDb() {
        //Room
        database = Room.databaseBuilder(this, AppDataBase::class.java, "meufii-db").allowMainThreadQueries().build()
    }

    fun initView() {
        etNome = findViewById(R.id.nome)
        etCodigo = findViewById(R.id.codigo)
        etDate = findViewById(R.id.data)
        btnRegistrar = findViewById(R.id.btn_registrar)
        btnRegistrar.setOnClickListener {
            register()
        }
    }

    fun register() {
        database?.ativoDao()?.insertAtivo(Ativo(etNome.text.toString(), etCodigo.text.toString()))
        finish()
    }
}
