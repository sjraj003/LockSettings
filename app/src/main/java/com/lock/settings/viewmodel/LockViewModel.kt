package com.lock.settings.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lock.settings.data.AppDatabase
import com.lock.settings.data.repo.MainRepository
import com.lock.settings.model.LockEntity
import com.lock.settings.network.model.LockConfigModel
import com.lock.settings.utils.log
import kotlinx.coroutines.launch

class LockViewModel(application: Application) : AndroidViewModel(application) {
    private val mainRepository: MainRepository
    private val _lockDetails = MutableLiveData<LockConfigModel>()
    private val _lockEntityList = MutableLiveData<List<LockEntity>>()
    var lockEntityListLiveData: LiveData<List<LockEntity>> = _lockEntityList
    var lockConfigLiveData: LiveData<LockConfigModel> = _lockDetails

    private val _lockEntity = MutableLiveData<LockEntity>()
    var lockEntityLiveData: LiveData<LockEntity> = _lockEntity

    init {
        val database = AppDatabase.getDatabase(application)
        mainRepository = MainRepository(database.mainDao())
    }

    /**
     * Fetch the Lock details from API,
     * Check the Local DB data, if there is no data store the api data into DB,
     * Get All data from DB and return to screen.
     */
    fun fetchLockDetails() {
        viewModelScope.launch {
            val locks = mainRepository.getLockConfiguration()
            var lists = mainRepository.lockEntities()
            if (lists.isEmpty()) {
                mainRepository.insertAndGetFromDB(locks)
                lists = mainRepository.lockEntities()
            }
            _lockDetails.value = locks
            _lockEntityList.value = lists
            log("_lockDetails :locksobject:" + locks)
        }
    }

    fun getLockValue(lockName: String) {
        viewModelScope.launch {
            val lockValue = mainRepository.getLockEntity(lockName)
            if (lockValue.isNotEmpty()) {
                _lockEntity.value = lockValue.get(0)
            }
        }
    }

    fun updateData(
        lockName: String,
        primaryValue: String?,
        secondaryValue: String?
    ) {
        viewModelScope.launch {
            mainRepository.updateEntity(lockName, primaryValue, secondaryValue)
        }
    }
}
