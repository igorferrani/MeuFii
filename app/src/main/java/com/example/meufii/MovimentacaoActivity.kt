package com.example.meufii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MovimentacaoActivity : AppCompatActivity() {

    private lateinit var etNome: EditText
    private lateinit var etCodigo: EditText
    private lateinit var etDate: EditText
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimentacao)

        initView()
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
        finish()
    }
}
