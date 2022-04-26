package com.example.retrofitpracticepokeapi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.model.PokemonResponse

@Database(
    entities = [Pokemon::class, PokemonResponse::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokeDao(): PokemonDao
    abstract fun pokemonRemoteKeysDao(): PokemonRemoteKeysDao
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "PokemonDb")
                .build()
    }
}