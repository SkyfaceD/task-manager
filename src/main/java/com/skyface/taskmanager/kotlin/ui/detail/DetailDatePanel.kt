package com.skyface.taskmanager.kotlin.ui.detail

import com.github.lgooddatepicker.components.DateTimePicker
import com.skyface.taskmanager.kotlin.utils.*
import java.awt.FlowLayout
import java.awt.Font
import java.awt.Rectangle
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION
import javax.swing.border.TitledBorder.DEFAULT_POSITION

class DetailDatePanel : JPanel(null) {

    private val dateTime = DateTimePicker()
    private val cbPanel = JPanel(FlowLayout(FlowLayout.LEFT))

    init {
        initDatePanel()
    }

    private fun initDatePanel() {
        bounds = Rectangle(10, EDIT_NAME_HEIGHT + EDIT_DESCRIPTION_HEIGHT + RADIO_PANEL_HEIGHT + 30, DATE_PANEL_WIDTH, DATE_PANEL_HEIGHT)
        background = DEFAULT_BACKGROUND
        foreground = COLOR_WHITE
        border = TitledBorder(
                LineBorder(BORDER_LINE_COLOR, 2, false),
                "Однократно",
                DEFAULT_JUSTIFICATION,
                DEFAULT_POSITION,
                Font(DEFAULT_FONT_NAME, Font.BOLD, 12),
                COLOR_WHITE
        )

        dateTime.apply {
            bounds = Rectangle(10, 25, DATE_PANEL_WIDTH - 20, 25)
            background = DEFAULT_BACKGROUND
            datePicker.setDateToToday()
            timePicker.text = SimpleDateFormat("HH:mm").format(Date(System.currentTimeMillis()))
        }

        val weekList = arrayListOf(
                JCheckBox("Воскресенье", true),
                JCheckBox("Понедельник"),
                JCheckBox("Вторник"),
                JCheckBox("Среда"),
                JCheckBox("Четверг"),
                JCheckBox("Пятница"),
                JCheckBox("Суббота")
        )

        val monthList = arrayListOf(
                JCheckBox("Январь", true),
                JCheckBox("Февраль"),
                JCheckBox("Март"),
                JCheckBox("Апрель"),
                JCheckBox("Май"),
                JCheckBox("Июнь"),
                JCheckBox("Июль"),
                JCheckBox("Август"),
                JCheckBox("Сентябрь"),
                JCheckBox("Октябрь"),
                JCheckBox("Ноябрь"),
                JCheckBox("Декабрь")
        )

        for (item in monthList) {
            item.background = DEFAULT_BACKGROUND
            item.foreground = COLOR_WHITE
            item.isFocusable = false
            item.icon = "/images/cb_idle.png".toIcon()
            item.pressedIcon = "/images/cb_idle.png".toIcon()
            item.selectedIcon = "/images/cb_selected.png".toIcon()
            cbPanel.add(item)
        }

        cbPanel.apply {
            background = DEFAULT_BACKGROUND
            bounds = Rectangle(10, dateTime.height + 30, DATE_PANEL_WIDTH - 20, 125)
        }

        add(dateTime)
        add(cbPanel)
    }
}