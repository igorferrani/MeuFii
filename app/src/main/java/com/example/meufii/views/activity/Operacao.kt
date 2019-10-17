package com.example.meufii.views.activity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "operacao")
data class Operacao(var nome: String,
                    var codigo: String,
                    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
                    var valorCota: Float = 0f,
                    var quantidadeCotas: Int = 0,
                    val valorRetornado: Float = 0f) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat()
    )

    @Ignore
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(codigo)
        parcel.writeString(uuid)
        parcel.writeFloat(valorCota)
        parcel.writeInt(quantidadeCotas)
        parcel.writeFloat(valorRetornado)
    }

    @Ignore
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Operacao> {
        @Ignore
        override fun createFromParcel(parcel: Parcel): Operacao {
            return Operacao(parcel)
        }

        @Ignore
        override fun newArray(size: Int): Array<Operacao?> {
            return arrayOfNulls(size)
        }
    }

    fun getValorInvestidoFormatado(): String {
        return UtilFormat.formatDecimal(valorCota * quantidadeCotas)
    }

    fun getValorRetornadoFormatado(): String {
        return UtilFormat.formatDecimal(valorRetornado)
    }
}