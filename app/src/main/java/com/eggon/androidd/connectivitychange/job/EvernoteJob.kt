package com.eggon.androidd.connectivitychange.job

import co.eggon.eggoid.extension.error
import com.evernote.android.job.Job

/**
 * EVERNOTE JOB
 */
class EvernoteJob : Job() {
    override fun onRunJob(params: Params): Result {
        "start ${params.id} job".error()
        return Result.SUCCESS
    }
}