package com.example.meufii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.room.Room


class MovimentacaoActivity : AppCompatActivity() {

    private var ativo: Ativo? = null

    private lateinit var etNome: EditText
    private lateinit var etCodigo: EditText
    private lateinit var etDate: EditText
    private lateinit var etValorCota: EditText
    private lateinit var etQuantidadeCotas: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnExcluir: Button

    companion object {
        var database: AppDataBase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimentacao)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)

        initView()
        initVariables()
        initBinding()
        initDb()
    }

    private fun initDb() {
        //Room
        database = Room.databaseBuilder(this, AppDataBase::class.java, "meufii2-db").allowMainThreadQueries().build()
    }

    private fun initView() {
        etNome = findViewById(R.id.nome)
        etCodigo = findViewById(R.id.codigo)
        etCodigo.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
        etDate = findViewById(R.id.data)
        etValorCota = findViewById(R.id.valor_cota)
        etQuantidadeCotas = findViewById(R.id.quantidade_cotas)
        btnRegistrar = findViewById(R.id.btn_registrar)
        btnRegistrar.setOnClickListener {
            save()
        }
        btnExcluir = findViewById(R.id.btn_excluir)
        btnExcluir.setOnClickListener {
            remove()
        }
    }

    private fun initVariables() {
        ativo = intent.getParcelableExtra("ativo")
    }

    private fun initBinding() {
        if (ativo != null) {
            etNome.setText(ativo!!.nome)
            etCodigo.setText(ativo!!.codigo)
            etValorCota.setText(ativo!!.valorCota.toString())
            etQuantidadeCotas.setText(ativo!!.quantidadeCotas.toString())

            // Exibe botão excluir
            btnExcluir.visibility = View.VISIBLE
        }
    }

    private fun save() {
        if (ativo != null) {
            ativo!!.nome = etNome.text.toString()
            ativo!!.codigo = etCodigo.text.toString()
            ativo!!.valorCota = etValorCota.text.toString().toFloat()
            ativo!!.quantidadeCotas = etQuantidadeCotas.text.toString().toInt()

            database?.ativoDao()?.updateAtivo(ativo!!)
        } else {
            database?.ativoDao()?.insertAtivo(Ativo(etNome.text.toString(),
                etCodigo.text.toString(),
                valorCota = etValorCota.text.toString().toFloat(),
                quantidadeCotas = etQuantidadeCotas.text.toString().toInt()))
        }
        finish()
    }

    private fun remove() {
        database?.ativoDao()?.deleteAtivo(ativo!!)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
