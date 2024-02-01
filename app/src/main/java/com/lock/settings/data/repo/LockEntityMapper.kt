package com.lock.settings.data.repo

import com.lock.settings.app.Constants.LOCK_ANGLE
import com.lock.settings.app.Constants.LOCK_KICK
import com.lock.settings.app.Constants.LOCK_RELEASE
import com.lock.settings.app.Constants.LOCK_RELEASE_TIME
import com.lock.settings.app.Constants.LOCK_TYPE
import com.lock.settings.app.Constants.LOCK_VOLTAGE
import com.lock.settings.model.Details
import com.lock.settings.model.LockEntity
import com.lock.settings.network.model.LockConfigModel

class LockEntityMapper(val configModel: LockConfigModel) {
    fun run(): List<LockEntity> {
        val list = ArrayList<LockEntity>()
        list.add(
            LockEntity(
                name = LOCK_VOLTAGE,
                details = Details("", "", defaults = configModel.lockVoltage?.default!!)
            )
        )
        list.add(
            LockEntity(
                name = LOCK_TYPE,
                details = Details("", "", defaults = configModel.lockType?.default!!)
            )
        )
        list.add(
            LockEntity(
                name = LOCK_KICK,
                details = Details("", "", defaults = configModel.lockKick?.default!!)
            )
        )
        list.add(
            LockEntity(
                name = LOCK_RELEASE,
                details = Details("", "", defaults = configModel.lockRelease?.default!!)
            )
        )
        list.add(
            LockEntity(
                name = LOCK_RELEASE_TIME,
                details = Details(
                    "",
                    "",
                    defaults = configModel.lockReleaseTime?.default.toString()
                )
            )
        )
        list.add(
            LockEntity(
                name = LOCK_ANGLE,
                details = Details("", "", defaults = configModel.lockAngle?.default.toString())
            )
        )
        return list
    }
}