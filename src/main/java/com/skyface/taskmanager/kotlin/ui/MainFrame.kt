package com.skyface.taskmanager.kotlin.ui

import com.skyface.taskmanager.kotlin.ui.detail.DetailComponent
import com.skyface.taskmanager.kotlin.utils.*
import java.awt.*
import javax.swing.*
import kotlin.system.exitProcess


object MainFrame : JFrame() {
    private val container = JPanel(null)

    private lateinit var weekList: List<JCheckBox>
    private lateinit var monthList: List<JCheckBox>

    private var path: String? = null

    private val confirmCreateTask = JButton()

    //New Era
    private val tray = TrayComponent()
    private val toolbar = ToolbarComponent()
    private val picker = PickerComponent()
    private val detail = DetailComponent

    init {
        tray.addTrayIcon()

        initComponents()
    }

    fun showFrame() {
        contentPane = container
        size = Dimension(CONTAINER_WIDTH, CONTAINER_HEIGHT)
        isUndecorated = true
        iconImage = "/images/logo_32x32.png".toImage()
        isVisible = true

        setLocationRelativeTo(null)
    }

    private fun initComponents() {
        container.size = Dimension(CONTAINER_WIDTH, CONTAINER_HEIGHT)

        container.apply {
            add(toolbar)
            add(picker)
            add(detail)
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
