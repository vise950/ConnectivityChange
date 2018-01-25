package com.eggon.androidd.connectivitychange.job

import com.eggon.androidd.connectivitychange.MainActivity
import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class JobCreator : JobCreator {
    override fun create(tag: String): Job? {
        return when (tag) {
            MainActivity.JOB_CREATOR -> {
                EvernoteJob()
            }
            else -> null
        }
    }
}