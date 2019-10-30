package com.br.meufii.views.ativo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.meufii.R
import com.br.meufii.adapter.OperacaoAdapter
import com.br.meufii.data.AppDataBase
import com.br.meufii.data.LocalDatabase
import com.br.meufii.model.Ativo
import com.br.meufii.model.Operacao
import com.br.meufii.views.home.HomeActivity
import com.br.meufii.views.operacao.OperacaoActivity

class AtivoActivity : AppCompatActivity() {

    private var ativo: Ativo? = null
    private var operacoes: List<Operacao>? = null
    private lateinit var uuidAtivo: String

    private lateinit var etNome: TextView
    private lateinit var rvOperacoes: RecyclerView
    private lateinit var btnAdicionarOperacao: Button
    private lateinit var toolbar: Toolbar

    private lateinit var adapterOperacao: OperacaoAdapter

    companion object {
        var database: AppDataBase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ativo)

        toolbar = findViewById(R.id.toolbar)

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
        buscaAtivoPorUuidAtivo()
        buscaOperacoresPorUuiddAtivo()
    }

    private fun buscaAtivoPorUuidAtivo() {
        ativo = database!!.ativoDao().getAtivo(uuidAtivo)
    }

    private fun buscaOperacoresPorUuiddAtivo() {
        operacoes = database!!.operacaoDao().getAllOperacoes(uuidAtivo)
    }

    private fun setTitleToolbar(title: String) {
        toolbar.findViewById<TextView>(R.id.toolbar_title).text = title
    }

    private fun initView() {
        etNome = findViewById(R.id.nome_ativo)
        rvOperacoes = findViewById(R.id.rv_operacoes)
        btnAdicionarOperacao = findViewById(R.id.btn_adicionar_operacao)
        btnAdicionarOperacao.setOnClickListener {
            openOperacao(uuidAtivo = ativo!!.uuid)
        }

        createAdapterOperacoes()
    }

    private fun initVariables() {
        uuidAtivo = intent.getStringExtra("uuidAtivo")
    }

    private fun initBinding() {
        if (ativo != null) {
            adapterOperacao.setItens(operacoes)
            setTitleToolbar(ativo!!.codigo)
            etNome.text = ativo!!.nome
        }
    }

    @SuppressLint("WrongConstant")
    private fun createAdapterOperacoes() {
        adapterOperacao = OperacaoAdapter(null)
        adapterOperacao.setOnItemClick {
            openOperacao(it, ativo!!.uuid)
        }
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvOperacoes.layoutManager = layout
        rvOperacoes.adapter = adapterOperacao
    }

    private fun openOperacao(operacao: Operacao? = null, uuidAtivo: String? = null) {
        val intent = Intent(this, OperacaoActivity::class.java)
        if (operacao != null) {
            intent.putExtra("operacao", operacao)
        }
        if (uuidAtivo != null) {
            intent.putExtra("uuidAtivo", uuidAtivo)
        }
        startActivityForResult(intent,
            HomeActivity.RC_HOME_ACTIVITY
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HomeActivity.RC_HOME_ACTIVITY) {
            setup()
            initBinding()
        }
    }
}
