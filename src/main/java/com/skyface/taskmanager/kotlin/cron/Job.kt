package com.skyface.taskmanager.kotlin.cron

import com.skyface.taskmanager.kotlin.database.Database
import com.skyface.taskmanager.kotlin.ui.MainFrame
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobKey

class Job : Job {
    private val mainFrame = MainFrame

    override fun execute(p: JobExecutionContext?) {
        p?.let {
            val dataMap = p.jobDetail.jobDataMap

            val key = dataMap["key"] as JobKey
            val name = dataMap["name"] as String
            val trigger = dataMap["trigger"] as Int

            ProcessBuilder("cmd", "/c", p.trigger.description).start()
            if (trigger == 0) {
                val task = mainFrame.taskList.find {
                    it.name == name
                }

                Database().removeData(name)
                mainFrame.scheduler.deleteJob(key)
                mainFrame.picker.removeFromPicker(task)
            }
        }
    }
}