package com.eggon.androidd.connectivitychange

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.eggon.eggoid.extension.error
import com.eggon.androidd.connectivitychange.job.BackgroundJob
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest


class MainActivity : AppCompatActivity() {

    companion object {
        private const val DELAY_START = 3000L
        private const val DELAY = 9000L

        private const val WIFI_JOB = 100
        private const val MOBILE_JOB = 101

        const val JOB_CREATOR = "job_creator"
        private const val WIFI_JOB_TAG = "wifi_job"
        private const val MOBILE_JOB_TAG = "mobile_job"
    }

    private val serviceComponent by lazy { ComponentName(this, BackgroundJob::class.java) }
    private val jobService by lazy { getSystemService(Context.JOB_SCHEDULER_SERVICE) as? JobScheduler }

    private val jobManager by lazy { JobManager.create(this) }

    /**
     * android job
     */
    private val wifiJobAndroid by lazy {
        JobInfo.Builder(WIFI_JOB, serviceComponent)
                .setMinimumLatency(DELAY_START)
                .setOverrideDeadline(DELAY_START + DELAY)
                .setPersisted(true)
                .build()
    }

    private val mobileJobAndroid by lazy {
        JobInfo.Builder(MOBILE_JOB, serviceComponent)
                .setMinimumLatency(DELAY_START)
                .setOverrideDeadline(DELAY_START + DELAY)
                .setPersisted(true)
                .build()
    }

    /**
     * evernote job
     */
    private val wifiJobEvernote by lazy {
        JobRequest.Builder(WIFI_JOB_TAG)
                .setExecutionWindow(DELAY_START, DELAY_START + DELAY)
                .setUpdateCurrent(true)
                .setRequirementsEnforced(true)
                .build()
    }

    private val mobileJobEvernote by lazy {
        JobRequest.Builder(MOBILE_JOB_TAG)
                .setExecutionWindow(DELAY_START, DELAY_START + DELAY)
                .setUpdateCurrent(true)
                .setRequirementsEnforced(true)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkStateReceiver.wifiConnection = {
            "wifi connection".error()
            jobService?.schedule(wifiJobAndroid)
//            wifiJobEvernote.schedule()
        }

        NetworkStateReceiver.mobileConnection = {
            "mobile connection".error()
            jobService?.schedule(mobileJobAndroid)
//            mobileJobEvernote.schedule()
        }

        NetworkStateReceiver.noConnection = {
            "no connection".error()
            jobService?.cancel(WIFI_JOB)
            jobService?.cancel(MOBILE_JOB)
//            jobManager.cancel(wifiJobEvernote.jobId)
//            jobManager.cancel(mobileJobEvernote.jobId)
        }
    }
}