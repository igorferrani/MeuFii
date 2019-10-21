package com.example.meufii.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.meufii.R
import com.example.meufii.data.AppDataBase
import com.example.meufii.data.LocalDatabase
import com.example.meufii.model.Operacao
import com.google.android.material.textfield.TextInputEditText


class OperacaoActivity : AppCompatActivity() {

    private var operacao: Operacao? = null

    private lateinit var etNome: TextInputEditText
    private lateinit var etCodigo: TextInputEditText
    private lateinit var etDate: TextInputEditText
    private lateinit var etValorCota: TextInputEditText
    private lateinit var etQuantidadeCotas: TextInputEditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnExcluir: Button

    companion object {
        var database: AppDataBase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimentacao)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.findViewById<TextView>(R.id.toolbar_title).text = resources.getString(
            R.string.title_operacao
        )

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        initView()
        initVariables()
        initBinding()
        initDb()
    }

    private fun initDb() {
        database = LocalDatabase.getInstance(this)
    }

    private fun initView() {
        etNome = findViewById(R.id.nome)
        etCodigo = findViewById(R.id.codigo)
        etCodigo.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
        etDate = findViewById(R.id.data)
        etValorCota = findViewById(R.id.valor_retornado)
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
        operacao = intent.getParcelableExtra("operacao")
    }

    private fun initBinding() {
        if (operacao != null) {
            etNome.setText(operacao!!.nome)
            etCodigo.setText(operacao!!.codigo)
            etValorCota.setText(operacao!!.valorCota.toString())
            etQuantidadeCotas.setText(operacao!!.quantidadeCotas.toString())

            // Exibe botão excluir
            btnExcluir.visibility = View.VISIBLE
        }
    }

    private fun save() {
        if (operacao != null) {
            operacao!!.nome = etNome.text.toString()
            operacao!!.codigo = etCodigo.text.toString()
            operacao!!.valorCota = etValorCota.text.toString().toDouble()
            operacao!!.quantidadeCotas = etQuantidadeCotas.text.toString().toInt()

            database?.operacaoDao()?.updateOperacao(operacao!!)
        } else {
            database?.operacaoDao()?.insertOperacao(
                Operacao(
                    etNome.text.toString(),
                    etCodigo.text.toString(),
                    valorCota = etValorCota.text.toString().toDouble(),
                    quantidadeCotas = etQuantidadeCotas.text.toString().toInt()
                )
            )
        }
        finish()
    }

    private fun remove() {
        database?.operacaoDao()?.deleteOperacao(operacao!!)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
