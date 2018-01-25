package com.eggon.androidd.connectivitychange.job

import android.app.job.JobParameters
import android.app.job.JobService
import co.eggon.eggoid.extension.error

class BackgroundJob : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        "start ${params?.jobId} job".error()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        "stop ${params?.jobId} job".error()
        return false
    }
}