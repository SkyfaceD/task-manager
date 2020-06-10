package com.skyface.taskmanager.kotlin.ui.detail

import com.github.lgooddatepicker.components.DateTimePicker
import com.skyface.taskmanager.kotlin.utils.*
import java.awt.FlowLayout
import java.awt.Font
import java.awt.Rectangle
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION
import javax.swing.border.TitledBorder.DEFAULT_POSITION

class DetailDatePanel : JPanel(null) {

    private val weekPanel = JPanel(FlowLayout(FlowLayout.LEFT))
    private val monthPanel = JPanel(FlowLayout(FlowLayout.LEFT))

    private val btnCancelTask = JButton()
    private val btnSaveTask = JButton()

    private val dateTime = DateTimePicker()

    private val weekList = arrayListOf(
            JCheckBox("Воскресенье", true),
            JCheckBox("Понедельник"),
            JCheckBox("Вторник"),
            JCheckBox("Среда"),
            JCheckBox("Четверг"),
            JCheckBox("Пятница"),
            JCheckBox("Суббота")
    )
    private val monthList = arrayListOf(
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

    val datePicker = dateTime.datePicker
    val timePicker = dateTime.timePicker

    init {
        initDatePanel()

        disableDate()

        setUpDatePanel()
    }

    fun updateCheckBoxes(trigger: Int, weeks: List<Int>, months: List<Int>) {
        when (trigger) {
            2 -> {
                for (item in weeks) {
                    weekList[item].isSelected = true
                }
            }
            3 -> {
                for (item in months) {
                    monthList[item].isSelected = true
                }
            }
        }
    }

    fun enableDate() {
        datePicker.isEnabled = true
        timePicker.isEnabled = true
        for (item in weekList) {
            item.isEnabled = true
        }
        for (item in monthList) {
            item.isEnabled = true
        }
    }

    fun disableDate() {
        datePicker.isEnabled = false
        timePicker.isEnabled = false
        for (item in weekList) {
            item.isEnabled = false
        }
        for (item in monthList) {
            item.isEnabled = false
        }
    }


    fun getGluedWeek(): String {
        var str = ""
        for (i in weekList.indices) {
            if (weekList[i].isSelected)
                str += "${i.inc()},"
        }
        return str.dropLast(1)
    }

    fun getGluedMonth(): String {
        var str = ""
        for (i in monthList.indices) {
            if (monthList[i].isSelected)
                str += "${i.inc()},"
        }
        return str.dropLast(1)
    }

    fun isWeekValid(): Boolean {
        for (item in weekList) {
            if (item.isSelected) {
                return true
            }
        }
        return false
    }

    fun isMonthValid(): Boolean {
        for (item in monthList) {
            if (item.isSelected) {
                return true
            }
        }
        return false
    }

    fun showCheckBoxes(isWeek: Boolean = false, isMonth: Boolean = false) {
        if (isWeek && isMonth) return

        weekPanel.isVisible = isWeek
        monthPanel.isVisible = isMonth
    }

    fun clearDatePanel() {
        for (item in weekList) {
            if (item.isSelected) {
                item.isSelected = false
            }
        }
        for (item in monthList) {
            if (item.isSelected) {
                item.isSelected = false
            }
        }

        weekList[0].isSelected = true
        monthList[0].isSelected = true

        datePicker.setDateToToday()
        timePicker.setTimeToNow()
    }

    private fun setUpDatePanel() {
        btnCancelTask.apply {
            addMouseListener(object : MouseAdapter() {
                override fun mouseEntered(e: MouseEvent?) {
                    changeButtonBackgroundAndForeground(
                            this@apply,
                            DELETE_BUTTON_BACKGROUND_HOVER,
                            COLOR_WHITE
                    )
                }

                override fun mouseExited(e: MouseEvent?) {
                    changeButtonBackgroundAndForeground(
                            this@apply,
                            DEFAULT_BACKGROUND,
                            COLOR_WHITE
                    )
                }
            })
        }

        btnSaveTask.apply {
            addMouseListener(object : MouseAdapter() {
                override fun mouseEntered(e: MouseEvent?) {
                    changeButtonBackgroundAndForeground(
                            this@apply,
                            CREATE_BUTTON_BACKGROUND_HOVER,
                            COLOR_WHITE
                    )
                }

                override fun mouseExited(e: MouseEvent?) {
                    changeButtonBackgroundAndForeground(
                            this@apply,
                            DEFAULT_BACKGROUND,
                            COLOR_WHITE
                    )
                }
            })
        }
    }

    private fun initDatePanel() {
        bounds = Rectangle(10, EDIT_NAME_HEIGHT + EDIT_DESCRIPTION_HEIGHT + TRIGGER_PANEL_HEIGHT + 30, DATE_PANEL_WIDTH, DATE_PANEL_HEIGHT)
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
            timePicker.setTimeToNow()
        }

        for (item in weekList) {
            initCheckBoxes(item)
            weekPanel.add(item)
        }

        for (item in monthList) {
            initCheckBoxes(item)
            monthPanel.add(item)
        }

        weekPanel.apply {
            bounds = Rectangle(0, dateTime.height + 30, DATE_PANEL_WIDTH - 20, 125)
            background = COLOR_TRANSPARENT
            isOpaque = false
            isVisible = false
        }

        monthPanel.apply {
            bounds = Rectangle(0, dateTime.height + 30, DATE_PANEL_WIDTH - 20, 125)
            background = COLOR_TRANSPARENT
            isOpaque = false
            isVisible = false
        }

        btnCancelTask.apply {
            bounds = Rectangle(10, dateTime.height + weekPanel.height + 35, DATE_PANEL_WIDTH / 2 - 10, 30)
            background = DEFAULT_BACKGROUND
            foreground = COLOR_WHITE
            border = null
            isFocusable = false
            text = "Отмена"
        }

        btnSaveTask.apply {
            bounds = Rectangle(btnCancelTask.width + 10, dateTime.height + weekPanel.height + 35, DATE_PANEL_WIDTH / 2 - 10, 30)
            background = DEFAULT_BACKGROUND
            foreground = COLOR_WHITE
            border = null
            isFocusable = false
            text = "Сохранить"
        }

        add(dateTime)
        add(weekPanel)
        add(monthPanel)
//        add(btnCancelTask)
//        add(btnSaveTask)
    }

    private fun initCheckBoxes(item: JCheckBox) {
        item.background = DEFAULT_BACKGROUND
        item.foreground = COLOR_WHITE
        item.isFocusable = false
        item.icon = "cb_idle.png".toIcon()
        item.pressedIcon = "cb_idle.png".toIcon()
        item.selectedIcon = "cb_selected.png".toIcon()
    }
}