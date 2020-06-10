package com.skyface.taskmanager.kotlin.ui

import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Dimension
import java.awt.Font
import java.awt.Rectangle
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class ToolbarComponent : JPanel(null) {
    private val mainFrame = MainFrame

    private val lblToolbar = JLabel()
    private val btnMinimize = JButton()
    private val btnClose = JButton()

    private var pX = 0
    private var pY = 0

    init {
        initToolbar()
        setUpToolbar()
    }

    private fun initToolbar() {
        size = Dimension(TOOLBAR_WIDTH, TOOLBAR_HEIGHT)
        background = TOOLBAR_BACKGROUND

        lblToolbar.icon = "logo_16x16.png".toIcon()
        lblToolbar.apply {
            bounds = Rectangle(10, 0, this@ToolbarComponent.width - 120, this@ToolbarComponent.height)
            foreground = COLOR_WHITE
            font = Font(DEFAULT_FONT_NAME, Font.BOLD, 12)
            text = APP_NAME
        }

        btnMinimize.apply {
            bounds = Rectangle(this@ToolbarComponent.width - 80, 0, 40, this@ToolbarComponent.height)
            background = MINIMIZE_BUTTON_BACKGROUND_IDLE
            foreground = MINIMIZE_BUTTON_FOREGROUND_IDLE
            isFocusable = false
            border = null
            font = Font(DEFAULT_FONT_NAME, Font.BOLD, 20)
            text = "─"
        }

        btnClose.apply {
            bounds = Rectangle(this@ToolbarComponent.width - 40, 0, 40, this@ToolbarComponent.height)
            background = MINIMIZE_BUTTON_BACKGROUND_IDLE
            foreground = MINIMIZE_BUTTON_FOREGROUND_IDLE
            isFocusable = false
            border = null
            font = Font("JetBrains Mono Medium", Font.PLAIN, 18)
            text = "×"
        }

        add(lblToolbar)
        add(btnMinimize)
        add(btnClose)
    }

    private fun setUpToolbar() {
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(event: MouseEvent) {
                pX = event.x
                pY = event.y
            }

            override fun mouseDragged(event: MouseEvent) {
                mainFrame.setLocation(
                        mainFrame.location.x + event.x - pX,
                        mainFrame.location.y + event.y - pY
                )
            }
        })
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(event: MouseEvent) {
                mainFrame.setLocation(
                        mainFrame.location.x + event.x - pX,
                        mainFrame.location.y + event.y - pY
                )
            }
        })

        btnMinimize.apply {
            addMouseListener(object : MouseAdapter() {
                override fun mouseEntered(event: MouseEvent) {
                    changeButtonBackgroundAndForeground(
                            btnMinimize,
                            MINIMIZE_BUTTON_BACKGROUND_HOVER,
                            MINIMIZE_BUTTON_FOREGROUND_HOVER
                    )
                }

                override fun mouseExited(event: MouseEvent) {
                    changeButtonBackgroundAndForeground(
                            btnMinimize,
                            MINIMIZE_BUTTON_BACKGROUND_IDLE,
                            MINIMIZE_BUTTON_FOREGROUND_IDLE
                    )
                }
            })
            
            addActionListener { mainFrame.minimizeApp() }
        }

        btnClose.apply {
            addMouseListener(object : MouseAdapter() {
                override fun mouseEntered(event: MouseEvent) {
                    changeButtonBackgroundAndForeground(
                            btnClose,
                            CLOSE_BUTTON_BACKGROUND_HOVER,
                            CLOSE_BUTTON_FOREGROUND_HOVER
                    )
                }
                
                override fun mouseExited(event: MouseEvent) {
                    changeButtonBackgroundAndForeground(
                            btnClose,
                            CLOSE_BUTTON_BACKGROUND_IDLE,
                            CLOSE_BUTTON_FOREGROUND_IDLE
                    )
                }
            })
            
            addActionListener { mainFrame.hideAppToTray() }
        }
    }
}