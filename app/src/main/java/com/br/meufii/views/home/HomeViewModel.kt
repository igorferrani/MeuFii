package com.br.meufii.views.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.meufii.data.AppDataBase
import com.br.meufii.model.CardResumoAtivo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HomeViewModel(var database: AppDataBase) : ViewModel() {

    val requestResumoLiveData = MutableLiveData<Document>()
    val processaResumoLiveData = MutableLiveData<ArrayList<String>>()
    var requestAtivosLocalLiveData = MutableLiveData<List<CardResumoAtivo>>()

    companion object {
        private const val ativo = "XPCM11"
        private const val url = "https://fiis.com.br/$ativo/?aba=tabela"
        private const val urlResumo = "https://fiis.com.br/resumo/"
    }

    fun buscaFiiAtivos() {
        GlobalScope.launch {
            requestResumoLiveData.postValue(Jsoup.connect(urlResumo).get())
        }
    }

    fun buscaAtivosLocal() {
        requestAtivosLocalLiveData.value = database.ativoDao().getAllCardResumoAtivos()
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
}