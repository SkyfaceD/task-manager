package com.skyface.taskmanager.kotlin.ui

import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Font
import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.Window
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION
import javax.swing.border.TitledBorder.DEFAULT_POSITION

class TrayComponent {
    private val mainFrame = MainFrame

    private val popUpMenu = JPopupMenu()
    private val popUpMenuFocus = JFrame()

    private val systemTray = SystemTray.getSystemTray()

    private val trayIcon = TrayIcon("logo_16x16.png".toImage(), APP_NAME)

    private lateinit var popUpMenuList: List<JMenuItem>

    private var isTrayIconAdded = false

    init {
        initTray()
        setUpTray()
    }

    fun addTrayIcon() {
        if (isTrayIconAdded) {
            println("Already added")
        } else {
            systemTray.add(trayIcon)
            isTrayIconAdded = true
        }
    }

    fun hidePopUpMenu() {
        if (!popUpMenu.isVisible || popUpMenuFocus.isVisible) return

        changePopUpMenuVisibility()
    }

    private fun changePopUpMenuVisibility(isVisible: Boolean = false) {
        popUpMenu.isVisible = isVisible
        popUpMenuFocus.isVisible = isVisible
    }

    private fun setUpTray() {
        popUpMenuFocus.addFocusListener(object : FocusAdapter() {
            override fun focusLost(e: FocusEvent?) {
                changePopUpMenuVisibility()
            }
        })

        popUpMenuList.apply {
            get(0).addActionListener { mainFrame.showApp() }
            get(1).addActionListener { println("Log") }
            get(2).addActionListener { mainFrame.closeApp() }
        }

        for (item in popUpMenuList) {
            item.addMouseListener(object : MouseAdapter() {
                override fun mouseEntered(event: MouseEvent) {
                    item.background = TRAY_ITEM_HOVER
                }

                override fun mouseExited(event: MouseEvent) {
                    item.background = TRAY_ITEM_IDLE
                }
            })
        }

        trayIcon.apply {
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        mainFrame.showApp()
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        popUpMenu.setLocation(e.x, e.y)
                        changePopUpMenuVisibility(true)
                    }
                }
            })
        }
    }

    private fun initTray() {
        popUpMenu.apply {
            background = TRAY_BACKGROUND
            border = TitledBorder(
                    BorderFactory.createCompoundBorder(
                            EmptyBorder(4, 6, 4, 6),
                            LineBorder(BORDER_LINE_COLOR, 2, true)
                    ),
                    APP_NAME,
                    DEFAULT_JUSTIFICATION,
                    DEFAULT_POSITION,
                    Font(DEFAULT_FONT_NAME, Font.BOLD, 12),
                    COLOR_WHITE
            )
        }

        popUpMenuFocus.apply {
            type = Window.Type.UTILITY
            isUndecorated = true
        }

        popUpMenuList = arrayListOf(
                JMenuItem(
                        "Развернуть".addMargin(12, 12, 4),
                        "menu_expand.png".toIcon()
                ),
                JMenuItem(
                        "Логи".addMargin(12, 12, 4),
                        "menu_log.png".toIcon()
                ),
                JMenuItem(
                        "Закрыть".addMargin(12, 12, 4),
                        "menu_close.png".toIcon()
                )
        )

        for (item in popUpMenuList) {
            item.apply {
                background = TRAY_ITEM_IDLE
                foreground = COLOR_WHITE
                border = null
            }
            popUpMenu.add(item)
        }
    }
}