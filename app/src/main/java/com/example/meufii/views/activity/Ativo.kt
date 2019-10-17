package com.example.meufii.views.activity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

data class Ativo(var nome: String,
                 var codigo: String,
                 var valorTotal: Float = 0f,
                 var quantidadeCotas: Int = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readInt()
    )

    @Ignore
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(codigo)
        parcel.writeFloat(valorTotal)
        parcel.writeInt(quantidadeCotas)
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

    fun getValorInvestidoFormatado(): String {
        return UtilFormat.formatDecimal(valorTotal)
    }
}