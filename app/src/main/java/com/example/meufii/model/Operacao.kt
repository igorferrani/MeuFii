package com.example.meufii.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "operacao")
data class Operacao(var nome: String = "",
                    var codigo: String = "",
                    var data: String = "",
                    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
                    var valorCota: Double = 0.0,
                    var quantidadeCotas: Int = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readInt()
    )

    @Ignore
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(codigo)
        parcel.writeString(data)
        parcel.writeString(uuid)
        parcel.writeDouble(valorCota)
        parcel.writeInt(quantidadeCotas)
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

    fun valorTotalOperacao() : Double {
        return quantidadeCotas * valorCota
    }
}