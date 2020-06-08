package com.skyface.taskmanager.kotlin.ui

import com.skyface.taskmanager.kotlin.custom.CustomScrollBarUI
import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Font
import java.awt.Rectangle
import javax.swing.BorderFactory
import javax.swing.JList
import javax.swing.JList.VERTICAL
import javax.swing.JScrollPane
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder

class PickerComponent : JScrollPane() {
    private val listPicker = JList<String>()

    init {
        initPicker()
        setUpPicker()
    }

    private fun setUpPicker() {
        setViewportView(listPicker)

        val pickerList = Array(33) {
            it.toString().addMargin(4, 4, 4, 4)
        }

        listPicker.setListData(pickerList)
    }

    private fun initPicker() {
        bounds = Rectangle(0, TOOLBAR_HEIGHT, PICKER_WIDTH, PICKER_HEIGHT)
        background = PICKER_BACKGROUND
        border = BorderFactory.createCompoundBorder(
                EmptyBorder(4, 4, 4, 4),
                TitledBorder(
                        LineBorder(BORDER_LINE_COLOR, 2, true),
                        "Задачи",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        Font(DEFAULT_FONT_NAME, Font.BOLD, 14),
                        COLOR_WHITE
                )
        )
        verticalScrollBar.setUI(CustomScrollBarUI())
        verticalScrollBar.background = background

        listPicker.apply {
            background = this@PickerComponent.background
            foreground = COLOR_WHITE
            selectionBackground = PICKER_ITEM_SELECTION_BACKGROUND
            selectionForeground = PICKER_ITEM_SELECTION_FOREGROUND
            font = Font(DEFAULT_FONT_NAME, Font.BOLD, 12)
            layoutOrientation = VERTICAL
            border = EmptyBorder(0, 6, 0, 6)
        }
    }
}