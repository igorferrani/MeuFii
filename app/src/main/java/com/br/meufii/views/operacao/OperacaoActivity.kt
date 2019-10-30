package com.br.meufii.views.operacao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.br.meufii.R
import com.br.meufii.data.AppDataBase
import com.br.meufii.data.LocalDatabase
import com.br.meufii.model.Ativo
import com.br.meufii.model.Operacao
import com.br.meufii.util.TextWatcherEditText
import com.br.meufii.views.ativo.BuscaAtivoActivity
import com.google.android.material.textfield.TextInputEditText


class OperacaoActivity : AppCompatActivity() {

    private var operacao: Operacao? = null
    private var ativo: Ativo? = null
    private var uuidAtivo: String? = null

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
        initDb()
        setup()
        initBinding()
    }

    private fun initDb() {
        database = LocalDatabase.getInstance(this)
    }

    private fun setup() {
        if (uuidAtivo != null) {
            ativoSelecionado = database!!.ativoDao().getAtivo(uuidAtivo!!)
        }
    }

    private fun initView() {
        tvNome = findViewById(R.id.tv_nome)
        tvCodigo = findViewById(R.id.tv_codigo)
        tvPlaceholder = findViewById(R.id.tv_placeholder_add_fundo)
        clCodigoFundo = findViewById(R.id.cl_codigo_fundo)
        clCodigoFundo.setOnClickListener {
            if (uuidAtivo == null) {
                val intent = Intent(this, BuscaAtivoActivity::class.java)
                startActivityForResult(intent, RC_BUSCA_ACTIVITY)
            }
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

            tvNome.text = ativoSelecionado.nome
            tvCodigo.text = ativoSelecionado.codigo
        } else {
            tvNome.visibility = View.GONE
            tvCodigo.visibility = View.GONE
            tvPlaceholder.visibility = View.VISIBLE
        }
    }

    private fun initVariables() {
        operacao = intent.getParcelableExtra("operacao")
        uuidAtivo = intent.getStringExtra("uuidAtivo")
    }

    private fun initBinding() {
        if (operacao != null) {
            etValorCota.setText(operacao!!.valorCota.toString())
            etQuantidadeCotas.setText(operacao!!.quantidadeCotas.toString())
            etDate.setText(operacao!!.data)

            toggleViewFundo(true)

            // Exibe botão excluir
            btnExcluir.visibility = View.VISIBLE
        }

        if (uuidAtivo != null) {
            toggleViewFundo(true)
        }
    }

    private fun save() {

        val etValor = etValorCota.text.toString().toDouble()
        val etQuantidade = etQuantidadeCotas.text.toString().toInt()
        val etData = etDate.text.toString()

        if (operacao != null && operacao!!.uuid.isNotEmpty()) {
            operacao!!.valorCota = etValor
            operacao!!.quantidadeCotas = etQuantidade
            operacao!!.data = etData

            database?.operacaoDao()?.updateOperacao(operacao!!)
        } else {
            database?.operacaoDao()?.insertOperacao(
                Operacao(
                    uuidAtivo = ativoSelecionado.uuid,
                    data = etData,
                    valorCota = etValor,
                    quantidadeCotas = etQuantidade
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_BUSCA_ACTIVITY) {
            if (data != null) {
                ativoSelecionadoMutable.value = data.getParcelableExtra("buscaAtivo")
            }
        }
    }
}
