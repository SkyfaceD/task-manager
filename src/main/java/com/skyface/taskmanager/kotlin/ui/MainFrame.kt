package com.skyface.taskmanager.kotlin.ui

import com.skyface.taskmanager.kotlin.cron.Job
import com.skyface.taskmanager.kotlin.database.Database
import com.skyface.taskmanager.kotlin.model.Task
import com.skyface.taskmanager.kotlin.ui.detail.DetailComponent
import com.skyface.taskmanager.kotlin.utils.*
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder.newJob
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.impl.StdSchedulerFactory
import java.awt.Color
import java.awt.Dimension
import java.awt.Frame
import java.awt.Rectangle
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.UIManager
import kotlin.system.exitProcess

object MainFrame : JFrame() {
    val taskList : MutableList<Task> = Database().getData()
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

        if (taskList.isNotEmpty()){
            scheduler.start()

            for ((index, item) in taskList.withIndex()){
                val job = newJob(Job::class.java)
                        .withIdentity("job$index")
                        .withDescription(item.trigger.toString())
                        .build()
                val trigger = newTrigger()
                        .withIdentity("trigger$index")
                        .withDescription(item.path)
                        .withSchedule(CronScheduleBuilder.cronSchedule(item.cron))
                        .build()

                scheduler.scheduleJob(job, trigger)
            }
        }

        tray.addTrayIcon()

        initComponents()

        println(taskList)
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
