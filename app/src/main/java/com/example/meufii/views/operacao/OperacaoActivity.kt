package com.example.meufii.views.operacao

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.meufii.R
import com.example.meufii.data.AppDataBase
import com.example.meufii.data.LocalDatabase
import com.example.meufii.model.Ativo
import com.example.meufii.model.Operacao
import com.example.meufii.util.TextWatcherEditText
import com.example.meufii.views.ativo.BuscaAtivoActivity
import com.google.android.material.textfield.TextInputEditText


class OperacaoActivity : AppCompatActivity() {

    private var operacao: Operacao? = null

    private lateinit var clCodigoFundo: ConstraintLayout
    private lateinit var tvCodigo: TextView
    private lateinit var tvNome: TextView
    private lateinit var tvPlaceholder: TextView
    private lateinit var etDate: TextInputEditText
    private lateinit var etValorCota: TextInputEditText
    private lateinit var etQuantidadeCotas: TextInputEditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnExcluir: Button

    private var ativoSelecionadoMutable = MutableLiveData<Ativo>()
    private var ativoSelecionado: Ativo = Ativo()

    companion object {
        private var database: AppDataBase? = null
        private var RC_BUSCA_ACTIVITY = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operacao)

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
        tvNome = findViewById(R.id.tv_nome)
        tvCodigo = findViewById(R.id.tv_codigo)
        tvPlaceholder = findViewById(R.id.tv_placeholder_add_fundo)
        clCodigoFundo = findViewById(R.id.cl_codigo_fundo)
        clCodigoFundo.setOnClickListener {
            val intent = Intent(this, BuscaAtivoActivity::class.java)
            startActivityForResult(intent, RC_BUSCA_ACTIVITY)
        }

        etDate = findViewById(R.id.data)
        etDate.addTextChangedListener(TextWatcherEditText.watcherDate(etDate))

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

        ativoSelecionadoMutable.observe(this, Observer {
            ativoSelecionado = it
            toggleViewFundo(true)
        })
    }

    private fun toggleViewFundo(exibeFundo: Boolean) {
        if (exibeFundo) {
            tvNome.visibility = View.VISIBLE
            tvCodigo.visibility = View.VISIBLE
            tvPlaceholder.visibility = View.GONE

            tvNome.setText(ativoSelecionado.nome)
            tvCodigo.setText(ativoSelecionado.codigo)
        } else {
            tvNome.visibility = View.GONE
            tvCodigo.visibility = View.GONE
            tvPlaceholder.visibility = View.VISIBLE
        }
    }

    private fun initVariables() {
        operacao = intent.getParcelableExtra("operacao")
    }

    private fun initBinding() {
        if (operacao != null) {
            etValorCota.setText(operacao!!.valorCota.toString())
            etQuantidadeCotas.setText(operacao!!.quantidadeCotas.toString())
            etDate.setText(operacao!!.data)

            ativoSelecionado.codigo = operacao!!.codigo
            ativoSelecionado.nome = operacao!!.nome

            operacao = null

            toggleViewFundo(true)

            // Exibe botão excluir
            btnExcluir.visibility = View.VISIBLE
        }
    }

    private fun save() {
        if (operacao != null) {
            operacao!!.valorCota = etValorCota.text.toString().toDouble()
            operacao!!.quantidadeCotas = etQuantidadeCotas.text.toString().toInt()
            operacao!!.data = etDate.text.toString()

            database?.operacaoDao()?.updateOperacao(operacao!!)
        } else {
            database?.operacaoDao()?.insertOperacao(
                Operacao(
                    ativoSelecionado.nome,
                    ativoSelecionado.codigo,
                    data = etDate.text.toString(),
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

    private fun setValorBuscaAtivo(ativo: Ativo?) {

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_BUSCA_ACTIVITY) {
            if (data != null) {
                ativoSelecionadoMutable.value = data.getParcelableExtra("buscaAtivo")
            }
        }
    }
}
