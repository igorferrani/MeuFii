package com.example.meufii

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class HomeActivity : AppCompatActivity() {

    private val ativo = "XPCM11"
    private val url = "https://fiis.com.br/$ativo/?aba=tabela"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    @SuppressLint("WrongConstant")
    fun initView() {
        val rv_ativos = findViewById<RecyclerView>(R.id.rv_ativos)
        val ativos = arrayListOf(Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"),Ativo("XP CORPORATE", "XPCM11"))
        val adapterAtivo = AtivoAdapter(ativos)
        adapterAtivo.setOnItemClickAtivo {
            val intent = Intent(this, MovimentacaoActivity::class.java)
            startActivity(intent)
        }
        rv_ativos.adapter = adapterAtivo
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_ativos.setLayoutManager(layout)
    }

    fun buscaAtivos() {
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
}
