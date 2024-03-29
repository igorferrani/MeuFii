package com.br.meufii.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ativo")
data class Ativo(@PrimaryKey val uuid: String = UUID.randomUUID().toString(),
                 var nome: String = "",
                 var codigo: String = "") : Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    @Ignore
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(nome)
        parcel.writeString(codigo)
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
}