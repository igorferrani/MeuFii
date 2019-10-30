package com.br.meufii.views.ativo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.meufii.R
import com.br.meufii.adapter.BuscaAtivoAdapter
import com.br.meufii.data.AppDataBase
import com.br.meufii.data.LocalDatabase
import com.br.meufii.model.Ativo
import com.br.meufii.views.operacao.OperacaoActivity

class BuscaAtivoActivity : AppCompatActivity() {

    private var itens: List<Ativo>? = null

    private lateinit var etBusca: EditText
    private lateinit var rvAtivos: RecyclerView
    private lateinit var toolbar: Toolbar

    private lateinit var adapter: BuscaAtivoAdapter

    companion object {
        private var database: AppDataBase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca_ativo)

        toolbar = findViewById(R.id.toolbar)
        toolbar.findViewById<TextView>(R.id.toolbar_title).text = getString(R.string.title_busca_ativo)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        initView()
        initDb()
        setup()
        initBinding()
    }

    private fun initDb() {
        database = LocalDatabase.getInstance(this)
    }

    private fun setup() {
        itens = database!!.ativoDao().getAllAtivos()
    }

    private fun initView() {
        adapter = BuscaAtivoAdapter(null)
        adapter.setOnItemClick {
            fechaActivity(it)
        }
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvAtivos = findViewById(R.id.rv_ativos)
        rvAtivos.setLayoutManager(layout)
        rvAtivos.adapter = adapter
    }

    private fun initBinding() {
        if (itens != null) {
            adapter.setItens(itens)
        }
    }

    private fun fechaActivity(ativo: Ativo? = null) {
        val intent = Intent(this, OperacaoActivity::class.java)
        intent.putExtra("buscaAtivo", ativo)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
