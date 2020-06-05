package com.skyface.taskmanager.kotlin.ui

import com.skyface.taskmanager.kotlin.custom.CustomScrollBarUI
import com.skyface.taskmanager.kotlin.utils.APP_NAME
import com.skyface.taskmanager.kotlin.utils.DEFAULT_TOOLBAR_BUTTON_BACKGROUND
import com.skyface.taskmanager.kotlin.utils.DEFAULT_TOOLBAR_BUTTON_FOREGROUND
import com.skyface.taskmanager.kotlin.utils.addMargin
import java.awt.*
import java.awt.event.*
import javax.swing.*
import javax.swing.JList.VERTICAL
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION
import javax.swing.border.TitledBorder.DEFAULT_POSITION
import kotlin.system.exitProcess


class ActivityMain : JFrame() {
    private val container = JPanel(null)

    private val toolbar = JPanel(null)
    private val lblToolbar = JLabel()
    private val btnMinimize = JButton()
    private val btnClose = JButton()

    private val listPicker = JList<String>()
    private val scrollPicker = JScrollPane()

    private val systemTray = SystemTray.getSystemTray()
    private val popUpMenu = JPopupMenu()
    private val popUpMenuFocus = JFrame()
    private lateinit var trayIcon: TrayIcon

    private val fontName = "JetBrainsMono-Regular"

    private var pX = 0
    private var pY = 0

    init {
        initComponents()
        setUpFonts()
        setUpComponents()

        initCustomPopUpMenu()
        initFrame()
    }

    private fun initCustomPopUpMenu() {
        popUpMenu.background = Color(0x17212B)
        popUpMenu.border = TitledBorder(
                LineBorder(DEFAULT_TOOLBAR_BUTTON_BACKGROUND, 4, true),
                APP_NAME,
                DEFAULT_JUSTIFICATION,
                DEFAULT_POSITION,
                Font(fontName, Font.BOLD, 12),
                Color.WHITE
        )

        popUpMenuFocus.type = Type.UTILITY
        popUpMenuFocus.isUndecorated = true
        popUpMenuFocus.addFocusListener(object : FocusAdapter() {
            override fun focusLost(e: FocusEvent?) {
                changePopUpMenuVisibility()
            }
        })

        val listOfMenus: List<JMenuItem> = arrayListOf(
                JMenuItem("Развернуть".addMargin(), ImageIcon(javaClass.getResource("/images/expand.png"))),
                JMenuItem("Логи".addMargin(), ImageIcon(javaClass.getResource("/images/log.png"))),
                JMenuItem("Закрыть".addMargin(), ImageIcon(javaClass.getResource("/images/exit.png")))
        ).apply {
            get(0).addActionListener { showAppFromTray() }
            get(1).addActionListener { println("Log") }
            get(2).addActionListener { closeApp() }
        }

        for (item in listOfMenus) {
            item.apply {
                background = Color(0x17212B)
                foreground = Color.WHITE
                border = null
                addMouseListener(object : MouseAdapter() {
                    override fun mouseEntered(event: MouseEvent) {
                        super.mouseEntered(event)
                        background = Color(0x2C3847)
                    }

                    override fun mouseExited(event: MouseEvent) {
                        super.mouseExited(event)
                        background = Color(0x17212B)
                    }
                })
            }
            popUpMenu.add(item)
        }

        trayIcon = TrayIcon(
                ImageIcon(javaClass.getResource("/images/icon_16x16.png")).image,
                APP_NAME
        ).apply {
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        showAppFromTray()
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        popUpMenu.setLocation(e.x, e.y)
                        changePopUpMenuVisibility(true)
                    }
                }
            })
        }
    }

    private fun changePopUpMenuVisibility(isVisible: Boolean = false) {
        popUpMenu.isVisible = isVisible
        popUpMenuFocus.isVisible = isVisible
    }

    private fun minimizeApp() {
        extendedState = ICONIFIED
    }

    private fun closeApp() {
        exitProcess(0)
    }

    private fun hideAppToTray() {
        isVisible = false

        systemTray.add(trayIcon)
    }

    private fun showAppFromTray() {
        isVisible = true

        systemTray.remove(trayIcon)
        changePopUpMenuVisibility()
    }

    private fun setUpComponents() {
        toolbar.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(event: MouseEvent) {
                pX = event.x
                pY = event.y
            }

            override fun mouseDragged(event: MouseEvent) {
                setLocation(location.x + event.x - pX, location.y + event.y - pY)
            }
        })
        toolbar.addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(event: MouseEvent) {
                setLocation(location.x + event.x - pX, location.y + event.y - pY)
            }
        })

        btnMinimize.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(event: MouseEvent) {
                super.mouseEntered(event)
                changeButtonColor(btnMinimize, Color(0x2C3847), Color(0xFFFFFF))
            }

            override fun mouseExited(event: MouseEvent) {
                super.mouseExited(event)
                changeButtonColor(btnMinimize)
            }
        })
        btnMinimize.addActionListener { minimizeApp() }

        btnClose.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(event: MouseEvent) {
                changeButtonColor(btnClose, Color(0xFF4F69), Color(0xFFFFFF))
            }

            override fun mouseExited(event: MouseEvent) {
                changeButtonColor(btnClose)
            }
        })
        btnClose.addActionListener { hideAppToTray() }
    }

    private fun changeButtonColor(
            btn: JButton,
            back: Color = DEFAULT_TOOLBAR_BUTTON_BACKGROUND,
            fore: Color = DEFAULT_TOOLBAR_BUTTON_FOREGROUND
    ) {
        btn.background = back
        btn.foreground = fore
    }

    private fun initComponents() {
        container.setSize(500, 500)
        container.background = Color(0x17212B)

        toolbar.bounds = Rectangle(0, 0, container.width, 30)
        toolbar.background = Color(0x0E1621)

        lblToolbar.apply {
            bounds = Rectangle(10, 0, toolbar.width - 120, toolbar.height)
            foreground = Color.WHITE
            text = APP_NAME
        }
        lblToolbar.icon = ImageIcon(javaClass.getResource("/images/icon_16x16.png"))

        btnMinimize.apply {
            bounds = Rectangle(toolbar.width - 80, 0, 40, toolbar.height)
            background = DEFAULT_TOOLBAR_BUTTON_BACKGROUND
            foreground = DEFAULT_TOOLBAR_BUTTON_FOREGROUND
            isFocusable = false
            border = null
            text = "─"
        }

        btnClose.apply {
            bounds = Rectangle(toolbar.width - 40, 0, 40, toolbar.height)
            background = DEFAULT_TOOLBAR_BUTTON_BACKGROUND
            foreground = DEFAULT_TOOLBAR_BUTTON_FOREGROUND
            isFocusable = false
            border = null
            text = "×"
        }

        toolbar.apply {
            add(lblToolbar)
            add(btnMinimize)
            add(btnClose)
        }

        val listOfNames = Array(100) {
            it.toString()
        }

        listPicker.setListData(listOfNames)

        listPicker.apply {
            background = Color(0x242F3D)
            foreground = Color.WHITE
            selectionBackground = Color(0x2C3847)
            selectionForeground = Color.WHITE
            layoutOrientation = VERTICAL
            border = BorderFactory.createEmptyBorder(0, 6, 0, 6)
        }

        scrollPicker.apply {
            bounds = Rectangle(0, toolbar.height, 200, container.height - toolbar.height)
            background = Color(0x242F3D)
            border = BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(4, 4, 4, 4),
                    TitledBorder(
                            LineBorder(DEFAULT_TOOLBAR_BUTTON_BACKGROUND, 2, true),
                            "Запланированные задачи",
                            DEFAULT_JUSTIFICATION,
                            DEFAULT_POSITION,
                            Font(fontName, Font.BOLD, 12),
                            Color.WHITE
                    )
            )
            verticalScrollBar.setUI(CustomScrollBarUI())
            verticalScrollBar.background = Color(0x242F3D)
            setViewportView(listPicker)
        }

        container.add(toolbar)
        container.add(scrollPicker)
    }

    private fun initFrame() {
        contentPane = container
        setSize(container.width, container.height)
        setLocationRelativeTo(container)
        isUndecorated = true
        iconImage = ImageIcon(javaClass.getResource("/images/icon_32x32.png")).image
        isVisible = true
    }

    /**
     * TODO idk how it works :/
     */
    private fun setUpFonts() {
        lblToolbar.font = Font(fontName, Font.BOLD, 12)
        btnMinimize.font = Font(fontName, Font.BOLD, 20)
        btnClose.font = Font("JetBrains Mono Medium", Font.PLAIN, 18)
    }
}
