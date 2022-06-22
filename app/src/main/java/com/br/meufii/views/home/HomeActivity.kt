package com.br.meufii.views.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.meufii.R
import com.br.meufii.adapter.AtivoAdapter
import com.br.meufii.adapter.UltimasOperacoesAdapter
import com.br.meufii.data.LocalDatabase
import com.br.meufii.model.CardResumoAtivo
import com.br.meufii.model.HomeResumoAtivo
import com.br.meufii.model.Operacao
import com.br.meufii.util.UtilFormat
import com.br.meufii.views.ativo.AtivoActivity
import com.br.meufii.views.operacao.OperacaoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private lateinit var buttonCompra: FloatingActionButton
    private lateinit var rvAtivos: RecyclerView
    private lateinit var rvUltimasOperacoes: RecyclerView
    private lateinit var adapterAtivo: AtivoAdapter
    private lateinit var adapterUltimasOperacoes: UltimasOperacoesAdapter
    private lateinit var tvValorTotalInvestido: TextView
    private lateinit var valorTotalRendimentos: TextView
    private lateinit var homeResumoAtivo: HomeResumoAtivo

    private lateinit var viewModel: HomeViewModel

    companion object {
        var RC_HOME_ACTIVITY = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.findViewById<TextView>(R.id.toolbar_title).text = resources.getString(
            R.string.title_home
        )

        initObservable()
        initView()
        setup()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        rvAtivos = findViewById(R.id.rv_ativos)
        rvUltimasOperacoes = findViewById(R.id.rv_ultimas_operacoes)
        tvValorTotalInvestido = findViewById(R.id.valor_total_investido)
        valorTotalRendimentos = findViewById(R.id.valor_total_rendimentos)
        buttonCompra = findViewById(R.id.btn_registrar_compra)

        configuraAdapterListaAtivos()
        configuraAdapterListaUltimasOperacoes()

        buttonCompra.setOnClickListener {
            openOperacao()
        }
    }

    private fun configuraAdapterListaAtivos() {
        adapterAtivo = AtivoAdapter(null)
        adapterAtivo.setOnItemClick {
            openAtivo(it)
        }

        rvAtivos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvAtivos.adapter = adapterAtivo
    }

    private fun configuraAdapterListaUltimasOperacoes() {
        adapterUltimasOperacoes = UltimasOperacoesAdapter(null)
        adapterUltimasOperacoes.setOnItemClick {
            openOperacao(it)
        }

        rvUltimasOperacoes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvUltimasOperacoes.adapter = adapterUltimasOperacoes
    }

    private fun setup() {
        viewModel.buscaResumoAtivosLocal()
        viewModel.buscaAtivosLocal()
        viewModel.buscaUltimasOperacoesLocal()
    }

    private fun initObservable() {
        viewModel = HomeViewModel(LocalDatabase.getInstance(this))

        viewModel.requestResumoLiveData.observe(this, Observer {
            viewModel.processaDadosResumoFii(it)
        })
        viewModel.processaResumoLiveData.observe(this, Observer {
            println(it)
        })
        viewModel.requestAtivosLocalLiveData.observe(this, Observer {
            setListAtivosAdapter(it)
        })
        viewModel.requestUltimasOperacoesLocalLiveData.observe(this, Observer {
            setListUltimasOperacoesAdapter(it)
        })
        viewModel.requestResumoAtivosLocalLiveData.observe(this, Observer {
            homeResumoAtivo = it
            setResumoView()
        })
    }

    private fun openOperacao() {
        val intent = Intent(this, OperacaoActivity::class.java)
        startActivityForResult(intent, RC_HOME_ACTIVITY)
    }

    private fun openAtivo(ativo: CardResumoAtivo? = null) {
        val intent = Intent(this, AtivoActivity::class.java)
        if (ativo != null) {
            intent.putExtra("uuidAtivo", ativo.uuid)
        }
        startActivityForResult(intent, RC_HOME_ACTIVITY)
    }

    private fun openOperacao(operacao: Operacao? = null) {
        val intent = Intent(this, OperacaoActivity::class.java)
        if (operacao != null) {
            intent.putExtra("operacao", operacao)
            intent.putExtra("uuidAtivo", operacao.uuidAtivo)
        }
        startActivityForResult(intent, RC_HOME_ACTIVITY)
    }

    private fun setListAtivosAdapter(ativos: List<CardResumoAtivo>?) {
        adapterAtivo.setItens(ativos)
    }

    private fun setListUltimasOperacoesAdapter(ativos: List<Operacao>?) {
        adapterUltimasOperacoes.setItens(ativos)
    }

    private fun setResumoView() {
        val valorTotalInvestido = UtilFormat.formatDecimal(homeResumoAtivo.valorInvestido)
        tvValorTotalInvestido.text = valorTotalInvestido
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_HOME_ACTIVITY) {
            setup()
        }
    }
}
