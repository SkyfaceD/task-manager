package com.skyface.taskmanager.kotlin.ui.detail

import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Font
import java.awt.Rectangle
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.border.TitledBorder.DEFAULT_POSITION
import javax.swing.border.TitledBorder.RIGHT

object DetailComponent : JPanel(null) {

    val datePanel = DetailDatePanel()
    val pathPanel = DetailPathPanel()
    val fields = DetailField()
    val triggers = DetailTrigger()
    val buttons = DetailButtons()

    init {
        initDetail()
    }

    fun setBorderTitle(title: String) {
        border = BorderFactory.createCompoundBorder(
                EmptyBorder(4, 4, 4, 4),
                TitledBorder(
                        LineBorder(BORDER_LINE_COLOR, 2, true),
                        title,
                        RIGHT,
                        DEFAULT_POSITION,
                        Font(DEFAULT_FONT_NAME, Font.BOLD, 14),
                        COLOR_WHITE
                )
        )
    }

    var sb = StringBuilder()

    fun validateComponents(): Boolean {
        sb.clear()

        if (fields.fieldName.text.isEmpty())
            sb.append("Заполните поле с наименованием задачи!\r\n")

        if (!datePanel.datePicker.isTextFieldValid)
            sb.append("Выберите корректную дату!\r\n")

        if (!datePanel.timePicker.isTextFieldValid)
            sb.append("Выберите корректное время!\r\n")

        when (triggers.trigger) {
            2 -> if (!datePanel.isWeekValid()) sb.append("Выберите хотя бы одну неделю!\r\n")
            3 -> if (!datePanel.isMonthValid()) sb.append("Выберите хотя бы один месяц!\r\n")
        }

        if (pathPanel.path.isEmpty())
            sb.append("Выберите путь до файла!\r\n")

        return sb.isEmpty()
    }

    private fun initDetail() {
        bounds = Rectangle(PICKER_WIDTH, TOOLBAR_HEIGHT, DETAIL_WIDTH, DETAIL_HEIGHT)
        background = DETAIL_BACKGROUND
        border = BorderFactory.createCompoundBorder(
                EmptyBorder(4, 4, 4, 4),
                TitledBorder(
                        LineBorder(BORDER_LINE_COLOR, 2, true),
                        "Просмотр",
                        RIGHT,
                        DEFAULT_POSITION,
                        Font(DEFAULT_FONT_NAME, Font.BOLD, 14),
                        COLOR_WHITE
                )
        )

        add(fields.fieldName)
        add(fields.fieldDescription)

        add(triggers.panel)
        add(datePanel)
        add(pathPanel)
        add(buttons.btnCancel)
        add(buttons.btnConfirm)
    }
}