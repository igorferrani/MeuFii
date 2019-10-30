package com.br.meufii.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Ignore
import com.br.meufii.util.UtilFormat

data class CardResumoAtivo(var uuid: String = "",
                           var nome: String = "",
                           var codigo: String = "",
                           var valorInvestido: Double = 0.0,
                           var quantidadeCotas: Int = 0,
                           var valorRendimento: Double = 0.0) : Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble()
    )

    companion object CREATOR : Parcelable.Creator<CardResumoAtivo> {
        @Ignore
        override fun createFromParcel(parcel: Parcel): CardResumoAtivo {
            return CardResumoAtivo(parcel)
        }

        @Ignore
        override fun newArray(size: Int): Array<CardResumoAtivo?> {
            return arrayOfNulls(size)
        }
    }

    @Ignore
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(codigo)
        parcel.writeDouble(valorInvestido)
        parcel.writeInt(quantidadeCotas)
        parcel.writeDouble(valorRendimento)
    }

    @Ignore
    override fun describeContents(): Int {
        return 0
    }

    fun getValorInvestidoFormatado(): String {
        return UtilFormat.formatDecimal(valorInvestido)
    }
}