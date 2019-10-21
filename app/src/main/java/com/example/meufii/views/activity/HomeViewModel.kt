package com.example.meufii.views.activity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meufii.data.LocalDatabase
import com.example.meufii.model.Ativo
import com.example.meufii.model.Operacao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HomeViewModel(context: Context) : ViewModel() {

    val requestResumoLiveData = MutableLiveData<Document>()
    val processaResumoLiveData = MutableLiveData<ArrayList<String>>()
    val requestAtivosLocalLiveData = MutableLiveData<List<Operacao>>()

    companion object {
        private const val ativo = "XPCM11"
        private const val url = "https://fiis.com.br/$ativo/?aba=tabela"
        private const val urlResumo = "https://fiis.com.br/resumo/"
    }

    private val database by lazy {
        LocalDatabase.getInstance(context)
    }

    fun buscaOperacoes() {
        requestAtivosLocalLiveData.value = database.operacaoDao().getAllOperacoes()
    }

    fun buscaFiiAtivos() {
        GlobalScope.launch {
            requestResumoLiveData.postValue(Jsoup.connect(urlResumo).get())
        }
    }

    fun processaDadosResumoFii(doc: Document) {
        val aux = ArrayList<String>()
        val tabela = doc.getElementById("tabela")
        val linhas = tabela.getElementsByTag("tr")

        for (linha in linhas) {
            for (coluna in linha.getElementsByTag("td")) {
                aux.add(coluna.text())
            }
        }
        processaResumoLiveData.value = aux
    }

    fun getListaAtivos(operacoes: List<Operacao>): List<Ativo> {
        return Ativo.agrupaOperacoesEmListaDeAtivos(operacoes)
    }

    fun getTotalInvestimento(ativos: List<Ativo>?): Double {
        var aux = 0.0
        if (ativos != null) {
            for (ativo: Ativo in ativos) {
                aux += ativo.getValorTotal()
            }
        }
        return aux
    }
}