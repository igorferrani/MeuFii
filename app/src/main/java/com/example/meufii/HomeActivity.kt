package com.example.meufii

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
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
        toolbar.findViewById<TextView>(R.id.toolbar_title).text = resources.getString(R.string.title_home)

        initView()
        initDb()
        setup()
    }

    @SuppressLint("WrongConstant")
    fun initView() {
        val rv_ativos = findViewById<RecyclerView>(R.id.rv_ativos)
        adapterAtivo = AtivoAdapter(null)
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

    fun openMovimentacao(ativo: Ativo? = null) {
        val intent = Intent(this, MovimentacaoActivity::class.java)
        if (ativo != null) {
            intent.putExtra("ativo", ativo)
        }
        startActivityForResult(intent, 123)
    }

    fun initDb() {
        //Room
        database = Room.databaseBuilder(this, AppDataBase::class.java, "meufii2-db")
            .allowMainThreadQueries()
            .build()
    }

    fun buscaAtivos(): List<Ativo>? {
        ativos = database?.ativoDao()?.getAllAtivos()
        return ativos
    }

    fun setup() {
        val ativos = buscaAtivos()
        adapterAtivo.setAtivos(ativos)
    }

    fun buscaFiiAtivos() {
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
