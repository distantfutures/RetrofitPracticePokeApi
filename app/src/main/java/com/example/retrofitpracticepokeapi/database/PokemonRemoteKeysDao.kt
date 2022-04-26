package com.example.retrofitpracticepokeapi.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitpracticepokeapi.model.PokemonResponse

@Dao
interface PokemonRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(remoteKey: List<PokemonResponse>)

    @Query("SELECT * FROM pokemon_remote_keys WHERE repoId = :repoId")
    fun remoteKeysRepoId(repoId: Int): PokemonResponse?

    @Query("DELETE FROM pokemon_remote_keys")
    fun clearRemoteKeys()
}