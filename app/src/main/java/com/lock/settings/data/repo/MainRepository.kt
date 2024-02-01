package com.lock.settings.data.repo

import com.lock.settings.data.MainDao
import com.lock.settings.model.LockEntity
import com.lock.settings.network.api.ApiConfiguration
import com.lock.settings.network.model.LockConfigModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainRepository(private val mainDao: MainDao) {
    suspend fun getLockConfiguration(): LockConfigModel {
        return ApiConfiguration.ipService.getLockConfiguration()
    }

    suspend fun insertAndGetFromDB(ipResponse: LockConfigModel?) {
        try {
            ipResponse?.let {
                val lists = LockEntityMapper(ipResponse).run()
                for (item in lists) {
                    insertLockEntity(item)
                }
            }
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun lockEntities(): List<LockEntity> {
        return mainDao.getAllLockItems()
    }

    private suspend fun insertLockEntity(entity: LockEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            mainDao.insertLockItem(entity)
        }
    }

    suspend fun getLockEntity(lockName: String): List<LockEntity> {
        return mainDao.getLockByName(lockName)
    }

    suspend fun updateEntity(
        lockName: String,
        primaryValue: String?,
        secondaryValue: String?
    ) {
        mainDao.updateByName(lockName, primaryValue, secondaryValue)
    }

}