package com.example.meufii.views.home

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
import com.example.meufii.util.UtilFormat
import com.example.meufii.views.ativo.AtivoActivity
import com.example.meufii.views.operacao.OperacaoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private var ativos: List<Ativo>? = null
    private lateinit var adapterAtivo: AtivoAdapter

    private var viewModel = HomeViewModel(this)

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
        val rv_ativos = findViewById<RecyclerView>(R.id.rv_ativos)
        adapterAtivo = AtivoAdapter(null)
        adapterAtivo.setOnItemClick {
            openAtivo(it)
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
        viewModel.requestAtivosLocalLiveData.observe(this, Observer {
            setListAdapter(viewModel.getListaAtivos(it))
        })
    }

    private fun openOperacao() {
        val intent = Intent(this, OperacaoActivity::class.java)
        startActivityForResult(intent, RC_HOME_ACTIVITY)
    }

    private fun openAtivo(ativo: Ativo? = null) {
        val intent = Intent(this, AtivoActivity::class.java)
        if (ativo != null) {
            intent.putExtra("codigoAtivo", ativo.codigo)
        }
        startActivityForResult(intent, RC_HOME_ACTIVITY)
    }

    private fun setup() {
        viewModel.buscaFiiAtivos()
        viewModel.buscaOperacoes()

        val valorTotalInvestido = findViewById<TextView>(R.id.valor_total_investido)
        valorTotalInvestido.text = UtilFormat.formatDecimal(viewModel.getTotalInvestimento(ativos))

        val valorTotalRendimentos = findViewById<TextView>(R.id.valor_total_rendimentos)
    }

    private fun setListAdapter(ativos: List<Ativo>) {
        adapterAtivo.setAtivos(ativos)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_HOME_ACTIVITY) {
            setup()
        }
    }
}
