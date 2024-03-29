package com.br.meufii.data

import android.content.Context
import androidx.room.Room

class LocalDatabase {

    companion object {
        var name = "meufii10-db"

        fun getInstance(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java,
                name
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}