package com.example.meufii.views.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.meufii.R
import com.example.meufii.adapter.AtivoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class HomeActivity : AppCompatActivity() {

    private val ativo = "XPCM11"
    private val url = "https://fiis.com.br/$ativo/?aba=tabela"
    private var ativos: List<Ativo>? = null
    private lateinit var adapterAtivo: AtivoAdapter


    companion object {
        var database: AppDataBase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.findViewById<TextView>(R.id.toolbar_title).text = resources.getString(
            R.string.title_home
        )

        initView()
        initDb()
        setup()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        val rv_ativos = findViewById<RecyclerView>(R.id.rv_ativos)
        adapterAtivo = AtivoAdapter(this, null)
        adapterAtivo.setOnItemClickAtivo {
            openMovimentacao(ativos!!.get(it))
        }
        val layout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_ativos.setLayoutManager(layout)
        rv_ativos.adapter = adapterAtivo

        var buttonCompra = findViewById<FloatingActionButton>(R.id.btn_registrar_compra)
        buttonCompra.setOnClickListener {
            openMovimentacao()
        }
    }

    private fun getTotalInvestimento(): Float {
        var aux: Float = 0f
        for (ativo: Ativo in ativos!!) {
            aux += ativo.valorTotal
        }
        return aux
    }

    private fun openMovimentacao(ativo: Ativo? = null) {
        val intent = Intent(this, MovimentacaoActivity::class.java)
        if (ativo != null) {
            intent.putExtra("ativo", ativo)
        }
        startActivityForResult(intent, 123)
    }

    private fun initDb() {
        database = Room.databaseBuilder(this, AppDataBase::class.java, "meufii4-db")
            .allowMainThreadQueries()
            .build()
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
                    ativo.quantidadeCotas += operacao.quantidadeCotas
                    ativo.valorTotal += operacao.valorCota * operacao.quantidadeCotas
                } else {
                    aux.add(
                        Ativo(
                            operacao.nome,
                            operacao.codigo,
                            operacao.valorCota * operacao.quantidadeCotas,
                            operacao.quantidadeCotas
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
        ativos = buscaOperacoes()
        adapterAtivo.setAtivos(ativos)

        val valorTotalInvestido = findViewById<TextView>(R.id.valor_total_investido)
        valorTotalInvestido.text = UtilFormat.formatDecimal(getTotalInvestimento())

        val valorTotalRendimentos = findViewById<TextView>(R.id.valor_total_rendimentos)
    }

    private fun buscaFiiAtivos() {
        GlobalScope.launch {
            val doc = Jsoup.connect(url).get()
            val tabela = doc.getElementById("tabela")

            val linhas = tabela.getElementsByTag("tr")

            for (linha in linhas) {
                for (coluna in linha.getElementsByTag("td")) {
                    println(coluna.text())
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            setup()
        }
    }
}
