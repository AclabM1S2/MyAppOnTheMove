package com.pdarcas.myapponthemove.data.repositories

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.pdarcas.myapponthemove.data.entities.RecordModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StorageRepository(private val context: Context) {

    fun saveRecordToInternalStorage(filename: String, record: RecordModel): Boolean {
        return try {
            context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(Json.encodeToString(record).toByteArray())
            }
            true
        }catch (e: Exception){
            Log.w(ContentValues.TAG, "failed to save record in local storage")
            false
        }
    }

    suspend fun loadRecordsFromInternalStorage(): List<RecordModel> {
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".otm") }?.map {
                Json.decodeFromString(String(it.readBytes()))
            } ?: listOf()
        }
    }
}