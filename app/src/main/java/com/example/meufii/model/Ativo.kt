package com.example.meufii.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Ignore
import com.example.meufii.views.activity.UtilFormat

data class Ativo(var nome: String,
                 var codigo: String,
                 var operacoes: ArrayList<Operacao> = ArrayList()) : Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readString().toString(),
        parcel.readString().toString(),
        arrayListOf<Operacao>().apply {
            parcel.readList(this as List<*>, Operacao::class.java.classLoader)
        }
    )

    @Ignore
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(codigo)
        parcel.writeList(operacoes as List<*>?)
    }

    @Ignore
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ativo> {
        @Ignore
        override fun createFromParcel(parcel: Parcel): Ativo {
            return Ativo(parcel)
        }

        @Ignore
        override fun newArray(size: Int): Array<Ativo?> {
            return arrayOfNulls(size)
        }
    }

    fun getValorTotal(): Double {
        var aux = 0.0
        for (operacao in operacoes) {
            aux += operacao.valorCota * operacao.quantidadeCotas
        }
        return aux
    }

    fun getQuantidadeCotas(): Int {
        var aux = 0
        for (operacao in operacoes) {
            aux += operacao.quantidadeCotas
        }
        return aux
    }

    fun getValorInvestidoFormatado(): String {
        return UtilFormat.formatDecimal(getValorTotal())
    }
}