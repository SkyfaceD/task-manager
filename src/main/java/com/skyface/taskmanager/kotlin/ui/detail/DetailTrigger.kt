package com.skyface.taskmanager.kotlin.ui.detail

import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Color
import java.awt.Font
import java.awt.Rectangle
import javax.swing.ButtonGroup
import javax.swing.JPanel
import javax.swing.JRadioButton
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION
import javax.swing.border.TitledBorder.DEFAULT_POSITION

class DetailTrigger {
    private val detailComponent = DetailComponent

    private val rbPanel = JPanel(null)
    private val rbGroup = ButtonGroup()

    private val rbOneTime = JRadioButton()
    private val rbEveryDay = JRadioButton()
    private val rbEveryWeek = JRadioButton()
    private val rbEveryMouth = JRadioButton()

    private lateinit var rbList: List<JRadioButton>

    val panel = rbPanel
    var trigger = 0

    init {
        initRadioButtons()

        disableTrigger()

        setUpRadioButtons()
    }

    fun enableTrigger() {
        for (item in rbList) {
            item.isEnabled = true
        }
    }

    fun disableTrigger() {
        for (item in rbList) {
            item.isEnabled = false
        }
    }

    fun updateTrigger(index: Int) {
        rbGroup.setSelected(rbList[index].model, true)

        trigger = index

        detailComponent.datePanel.border = TitledBorder(
                LineBorder(BORDER_LINE_COLOR, 2, false),
                rbList[index].text,
                DEFAULT_JUSTIFICATION,
                DEFAULT_POSITION,
                Font(DEFAULT_FONT_NAME, Font.BOLD, 12),
                COLOR_WHITE
        )

        when (index) {
            0, 1 -> detailComponent.datePanel.showCheckBoxes()
            2 -> detailComponent.datePanel.showCheckBoxes(isWeek = true)
            3 -> detailComponent.datePanel.showCheckBoxes(isMonth = true)
        }
    }

    fun clearTrigger() {
        rbGroup.setSelected(rbList[0].model, true)

        trigger = 0

        detailComponent.datePanel.border = TitledBorder(
                LineBorder(BORDER_LINE_COLOR, 2, false),
                rbList[0].text,
                DEFAULT_JUSTIFICATION,
                DEFAULT_POSITION,
                Font(DEFAULT_FONT_NAME, Font.BOLD, 12),
                COLOR_WHITE
        )
        detailComponent.datePanel.showCheckBoxes()
    }

    private fun setUpRadioButtons() {
        for (item in rbList) {
            item.addActionListener {
                trigger = when (item.text) {
                    "Однократно" -> {
                        detailComponent.datePanel.showCheckBoxes()
                        0
                    }
                    "Ежедневно" -> {
                        detailComponent.datePanel.showCheckBoxes()
                        1
                    }
                    "Еженедельно" -> {
                        detailComponent.datePanel.showCheckBoxes(isWeek = true)
                        2
                    }
                    "Ежемесячно" -> {
                        detailComponent.datePanel.showCheckBoxes(isMonth = true)
                        3
                    }
                    else -> throw Exception("Trigger Exception!")
                }

                detailComponent.datePanel.border = TitledBorder(
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
            bounds = Rectangle(10, EDIT_NAME_HEIGHT + EDIT_DESCRIPTION_HEIGHT + 30, TRIGGER_PANEL_WIDTH, TRIGGER_PANEL_HEIGHT)
            background = DEFAULT_BACKGROUND
            foreground = COLOR_WHITE
            border = TitledBorder(
                    LineBorder(BORDER_LINE_COLOR, 2, false),
                    "Триггер",
                    DEFAULT_JUSTIFICATION,
                    DEFAULT_POSITION,
                    Font(DEFAULT_FONT_NAME, Font.BOLD, 12),
                    COLOR_WHITE
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
        rb.icon = "rb_idle.png".toIcon()
        rb.pressedIcon = "rb_idle.png".toIcon()
        rb.selectedIcon = "rb_selected.png".toIcon()
    }
}