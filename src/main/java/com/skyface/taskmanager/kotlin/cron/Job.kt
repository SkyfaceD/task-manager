package com.skyface.taskmanager.kotlin.cron

import com.skyface.taskmanager.kotlin.ui.MainFrame
import org.quartz.Job
import org.quartz.JobExecutionContext

class Job : Job {
    private val mainFrame = MainFrame

    override fun execute(p: JobExecutionContext?) {
        p?.let {
            ProcessBuilder("cmd", "/c", p.trigger.description).start()
            if(p.jobDetail.description == "0"){
                mainFrame.picker.removeFromPicker()
            }
        }
    }
}