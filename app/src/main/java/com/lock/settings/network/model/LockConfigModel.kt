package com.lock.settings.network.model


data class LockConfigModel(
    val lockVoltage: lockVoltage?,
    val lockType: lockType?,
    val lockKick: lockKick?,
    val lockRelease: lockRelease?,
    val lockReleaseTime: lockReleaseTime?,
    val lockAngle: lockAngle?
)

data class lockVoltage(
    val default: String?,
    val values: Array<String?>?
)

data class lockType(
    val default: String?,
    val values: Array<String?>?
)

data class lockKick(
    val default: String?,
    val values: Array<String?>?
)


data class lockRelease(
    val default: String?,
    val values: Array<String?>?,
    val common: Boolean?
)


data class lockReleaseTime(
    val range: Range?,
    val default: Float?,
    val unit: String?
)

data class lockAngle(
    val range: Range?,
    val default: Float?,
    val unit: String?,
    val common: Boolean?
)

data class Range(
    val min: Float?,
    val max: Float?
)

interface ResponseListener {
    fun onSuccess(value: LockConfigModel?)
    fun onError(error: String?)
}