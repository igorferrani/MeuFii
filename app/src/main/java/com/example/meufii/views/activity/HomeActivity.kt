package com.example.meufii.views.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meufii.R
import com.example.meufii.adapter.AtivoAdapter
import com.example.meufii.model.Ativo
import com.example.meufii.model.Operacao
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private var ativos: List<Ativo>? = null
    private lateinit var adapterAtivo: AtivoAdapter

    private var viewModel = HomeViewModel()

    companion object {
        var RC_HOME_ACTIVITY = 1
        var database: AppDataBase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.findViewById<TextView>(R.id.toolbar_title).text = resources.getString(
            R.string.title_home
        )

        initObservable()
        initView()
        initDb()
        setup()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        val rv_ativos = findViewById<RecyclerView>(R.id.rv_ativos)
        adapterAtivo = AtivoAdapter(null)
        adapterAtivo.setOnItemClick {
            openAtivo(ativos!!.get(it))
        }
        val layout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_ativos.setLayoutManager(layout)
        rv_ativos.adapter = adapterAtivo

        var buttonCompra = findViewById<FloatingActionButton>(R.id.btn_registrar_compra)
        buttonCompra.setOnClickListener {
            openOperacao()
        }
    }

    private fun initObservable() {
        viewModel.requestResumoLiveData.observe(this, Observer {
            viewModel.processaDadosResumoFii(it)
        })
        viewModel.processaResumoLiveData.observe(this, Observer {
            println(it)
        })
    }

    private fun getTotalInvestimento(): Double {
        var aux = 0.0
        for (ativo: Ativo in ativos!!) {
            aux += ativo.getValorTotal()
        }
        return aux
    }

    private fun openOperacao() {
        val intent = Intent(this, OperacaoActivity::class.java)
        startActivityForResult(intent, RC_HOME_ACTIVITY)
    }

    private fun openAtivo(ativo: Ativo? = null) {
        val intent = Intent(this, AtivoActivity::class.java)
        if (ativo != null) {
            intent.putExtra("ativo", ativo)
        }
        startActivityForResult(intent, RC_HOME_ACTIVITY)
    }

    private fun initDb() {
        database = LocalDatabase.getInstance(this)
    }

    private fun buscaOperacoes(): List<Ativo> {
        val aux =  database?.operacaoDao()?.getAllOperacoes()
        return processaListaOperacoes(aux)
    }

    private fun processaListaOperacoes(operacoes: List<Operacao>?): List<Ativo> {
        val aux = ArrayList<Ativo>()
        if (operacoes != null) {
            for (operacao in operacoes) {
                val ativo = verificaSePossuiOperacaoEmAtivos(aux, operacao)
                if (ativo != null) {
                    ativo.operacoes.add(operacao)
                } else {
                    aux.add(
                        Ativo(
                            operacao.nome,
                            operacao.codigo,
                            operacoes = arrayListOf(operacao)
                        )
                    )
                }
            }
        }
        return aux
    }

    private fun verificaSePossuiOperacaoEmAtivos(ativos: List<Ativo>, operacao: Operacao): Ativo? {
        for (ativo in ativos) {
            if (ativo.codigo.equals(operacao.codigo)) {
                return ativo
            }
        }
        return null
    }

    private fun setup() {
        viewModel.buscaFiiAtivos()
        ativos = buscaOperacoes()
        adapterAtivo.setAtivos(ativos)

        val valorTotalInvestido = findViewById<TextView>(R.id.valor_total_investido)
        valorTotalInvestido.text = UtilFormat.formatDecimal(getTotalInvestimento())

        val valorTotalRendimentos = findViewById<TextView>(R.id.valor_total_rendimentos)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_HOME_ACTIVITY) {
            setup()
        }
    }
}
