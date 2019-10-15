package com.example.meufii

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class HomeActivity : AppCompatActivity() {

    private val ativo = "XPCM11"
    private val url = "https://fiis.com.br/$ativo/?aba=tabela"
    //private val ativos = arrayListOf(Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"))
    private lateinit var adapterAtivo: AtivoAdapter


    companion object {
        var database: AppDataBase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initDb()
        setup()
    }

    @SuppressLint("WrongConstant")
    fun initView() {
        val rv_ativos = findViewById<RecyclerView>(R.id.rv_ativos)
        adapterAtivo = AtivoAdapter(null)
        adapterAtivo.setOnItemClickAtivo {
            openMovimentacao()
        }
        rv_ativos.adapter = adapterAtivo
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_ativos.setLayoutManager(layout)

        var buttonCompra = findViewById<Button>(R.id.btn_registrar_compra)
        buttonCompra.setOnClickListener {
            openMovimentacao()
        }

        var buttonVenda = findViewById<Button>(R.id.btn_registrar_venda)
        buttonVenda.setOnClickListener {
            openMovimentacao()
        }
    }

    fun openMovimentacao() {
        val intent = Intent(this, MovimentacaoActivity::class.java)
        startActivityForResult(intent, 123)
    }

    fun initDb() {
        //Room
        database = Room.databaseBuilder(this, AppDataBase::class.java, "meufii-db").allowMainThreadQueries().build()
    }

    fun buscaAtivos(): List<Ativo>? {
        val ativos = database?.ativoDao()?.getAllAtivos()
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
