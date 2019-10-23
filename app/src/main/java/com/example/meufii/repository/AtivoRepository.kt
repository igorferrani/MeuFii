package com.example.meufii.repository

import androidx.lifecycle.MutableLiveData
import com.example.meufii.model.Ativo

class AtivoRepository {

    companion object {
        private lateinit var instance: AtivoRepository
        private var ativoLiveData = MutableLiveData<List<Ativo>>()

        fun getInstance(): AtivoRepository {
            if (instance == null) {
                instance = AtivoRepository()
            }
            return instance
        }
    }

    fun getAtivoLiveData(): MutableLiveData<List<Ativo>> {
        return ativoLiveData
    }
}