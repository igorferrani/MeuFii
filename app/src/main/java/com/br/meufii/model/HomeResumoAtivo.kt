package com.br.meufii.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Ignore

data class HomeResumoAtivo(var valorInvestido: Double = 0.0,
                           var valorRendimento: Double = 0.0) : Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readDouble(),
        parcel.readDouble()
    )

    companion object CREATOR : Parcelable.Creator<HomeResumoAtivo> {
        @Ignore
        override fun createFromParcel(parcel: Parcel): HomeResumoAtivo {
            return HomeResumoAtivo(parcel)
        }

        @Ignore
        override fun newArray(size: Int): Array<HomeResumoAtivo?> {
            return arrayOfNulls(size)
        }
    }

    @Ignore
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(valorInvestido)
        parcel.writeDouble(valorRendimento)
    }

    @Ignore
    override fun describeContents(): Int {
        return 0
    }
}