package com.skyface.taskmanager.kotlin.ui.detail

import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Color
import java.awt.Font
import java.awt.Rectangle
import javax.swing.BorderFactory
import javax.swing.ButtonGroup
import javax.swing.JPanel
import javax.swing.JRadioButton
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION
import javax.swing.border.TitledBorder.DEFAULT_POSITION

class DetailRadioButtons {
    private val detail = DetailComponent

    val rbPanel = JPanel(null)

    private val rbGroup = ButtonGroup()
    private val rbOneTime = JRadioButton()
    private val rbEveryDay = JRadioButton()
    private val rbEveryWeek = JRadioButton()
    private val rbEveryMouth = JRadioButton()

    private lateinit var rbList: List<JRadioButton>

    init {
        initRadioButtons()
        setUpRadioButtons()
    }

    private fun setUpRadioButtons() {
        for (item in rbList) {
            item.addActionListener {
                detail.trigger = when (item.text) {
                    "Однократно" -> {
//                        datePanelComponentsVisibility()
                        0
                    }
                    "Ежедневно" -> {
//                        datePanelComponentsVisibility()
                        1
                    }
                    "Еженедельно" -> {
//                        datePanelComponentsVisibility(isWeek = true)
                        2
                    }
                    "Ежемесячно" -> {
//                        datePanelComponentsVisibility(isMonth = true)
                        3
                    }
                    else -> throw Exception("Trigger Exception!")
                }

                detail.datePanel.border = TitledBorder(
                        LineBorder(BORDER_LINE_COLOR, 2, false),
                        item.text,
                        DEFAULT_JUSTIFICATION,
                        DEFAULT_POSITION,
                        Font(DEFAULT_FONT_NAME, Font.BOLD, 12),
                        COLOR_WHITE
                )
            }
        }
    }

    private fun initRadioButtons() {
        rbPanel.apply {
            bounds = Rectangle(10, EDIT_NAME_HEIGHT + EDIT_DESCRIPTION_HEIGHT + 30, RADIO_PANEL_WIDTH, RADIO_PANEL_HEIGHT)
            background = DEFAULT_BACKGROUND
            foreground = COLOR_WHITE
            border = BorderFactory.createCompoundBorder(
                    TitledBorder(
                            LineBorder(BORDER_LINE_COLOR, 2, false),
                            "Триггер",
                            DEFAULT_JUSTIFICATION,
                            DEFAULT_POSITION,
                            Font(DEFAULT_FONT_NAME, Font.BOLD, 12),
                            COLOR_WHITE
                    ),
                    EmptyBorder(0, 0, 0, 0)
            )
        }

        rbList = arrayListOf(rbOneTime, rbEveryDay, rbEveryWeek, rbEveryMouth)

        createRadioButton(rbOneTime, Rectangle(10, 15, 130, 30), "Однократно")
        createRadioButton(rbEveryDay, Rectangle(150, 15, 130, 30), "Ежедневно")
        createRadioButton(rbEveryWeek, Rectangle(10, 45, 130, 30), "Еженедельно")
        createRadioButton(rbEveryMouth, Rectangle(150, 45, 130, 30), "Ежемесячно")

        for (btn in rbList) {
            rbPanel.add(btn)
            rbGroup.add(btn)
        }

        rbGroup.setSelected(rbList[0].model, true)
    }

    private fun createRadioButton(rb: JRadioButton, rectangle: Rectangle, text: String) {
        rb.bounds = rectangle
        rb.background = Color(0x00000000, true)
        rb.foreground = COLOR_WHITE
        rb.isOpaque = false
        rb.isFocusable = false
        rb.text = text
        rb.icon = "/images/rb_idle.png".toIcon()
        rb.pressedIcon = "/images/rb_idle.png".toIcon()
        rb.selectedIcon = "/images/rb_selected.png".toIcon()
    }
}