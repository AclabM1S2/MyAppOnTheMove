package com.pdarcas.myapponthemove.ui.fragments.records

import androidx.lifecycle.ViewModel
import com.pdarcas.myapponthemove.data.entities.RecordModel
import com.pdarcas.myapponthemove.data.repositories.StorageRepository

class RecordsViewModel(private val storageRepository: StorageRepository): ViewModel() {

    fun saveRecord(record: RecordModel) = storageRepository.saveRecordToInternalStorage(record.name, record)
    suspend fun getRecords() = storageRepository.loadRecordsFromInternalStorage()
}