package com.skyface.taskmanager.kotlin.ui

import com.skyface.taskmanager.kotlin.cron.Job
import com.skyface.taskmanager.kotlin.database.Database
import com.skyface.taskmanager.kotlin.model.Task
import com.skyface.taskmanager.kotlin.ui.detail.DetailComponent
import com.skyface.taskmanager.kotlin.utils.CONTAINER_HEIGHT
import com.skyface.taskmanager.kotlin.utils.CONTAINER_WIDTH
import com.skyface.taskmanager.kotlin.utils.DEFAULT_BACKGROUND
import com.skyface.taskmanager.kotlin.utils.toImage
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder.newJob
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.impl.StdSchedulerFactory
import java.awt.Dimension
import java.awt.Frame
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.UIManager
import kotlin.system.exitProcess

object MainFrame : JFrame() {
    val taskList: MutableList<Task> = Database().getData()
    val scheduler = StdSchedulerFactory.getDefaultScheduler()

    private val detail = DetailComponent

    private val container = JPanel(null)

    private val tray = TrayComponent()
    private val toolbar = ToolbarComponent()
    val buttons = ButtonComponents()

    val picker = PickerComponent()

    var createMode = false

    init {
        UIManager.put("OptionPane.yesButtonText", "Да")
        UIManager.put("OptionPane.noButtonText", "Нет")

        tray.addTrayIcon()

        initComponents()

        println(taskList)
    }

    fun addJob() {
        val task = taskList.last()

        val job = newJob(Job::class.java)
                .withIdentity("job${task.name}", "group1")
                .build()

        job.jobDataMap.apply {
            set("key", job.key)
            set("name", task.name)
            set("trigger", task.trigger)
            set("path", task.path)
        }

        val trigger = newTrigger()
                .withIdentity("trigger${task.name}", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(task.cron))
                .build()

        try {
            scheduler.scheduleJob(job, trigger)
        } catch (e: Exception) {
            scheduler.deleteJob(job.key)
        }
    }

    fun addJobs() {
        if (taskList.isNotEmpty()) {
            for (item in taskList) {
                val job = newJob(Job::class.java)
                        .withIdentity("job${item.name}", "group1")
                        .build()

                job.jobDataMap.apply {
                    set("key", job.key)
                    set("name", item.name)
                    set("trigger", item.trigger)
                    set("path", item.path)
                }

                val trigger = newTrigger()
                        .withIdentity("trigger${item.name}", "group1")
                        .withSchedule(CronScheduleBuilder.cronSchedule(item.cron))
                        .build()

                try {
                    scheduler.scheduleJob(job, trigger)
                } catch (e: Exception) {
                    scheduler.deleteJob(job.key)
                }
            }

            scheduler.start()
        }
    }

    fun showFrame() {
        contentPane = container
        size = Dimension(CONTAINER_WIDTH, CONTAINER_HEIGHT)
        isUndecorated = true
        iconImage = "logo_32x32.png".toImage()
        isVisible = true

        setLocationRelativeTo(null)
    }

    private fun initComponents() {
        container.size = Dimension(CONTAINER_WIDTH, CONTAINER_HEIGHT)
        container.background = DEFAULT_BACKGROUND

        container.apply {
            add(toolbar)
            add(picker)
            add(detail)
            add(buttons.btnDeleteTask)
            add(buttons.btnEditTask)
            add(buttons.btnCreateTask)
        }
    }

    /**
     * UI Control?
     */

    fun showApp() {
        if (isVisible && extendedState == Frame.NORMAL) {
            toFront()
            return
        }

        if (isVisible) {
            normalizeApp()
        } else {
            showAppFromTray()
        }
    }

    fun closeApp() {
        exitProcess(0)
    }

    fun minimizeApp() {
        extendedState = ICONIFIED
    }

    fun hideAppToTray() {
        isVisible = false
    }

    private fun normalizeApp() {
        extendedState = NORMAL
    }

    private fun showAppFromTray() {
        isVisible = true
        tray.hidePopUpMenu()
    }
}
