package com.skyface.taskmanager.kotlin.ui.detail

import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Font
import java.awt.Rectangle
import javax.swing.BorderFactory
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION
import javax.swing.border.TitledBorder.DEFAULT_POSITION

class DetailField {
    private val edtName = JTextField()
    private val edtDescription = JTextArea()

    val fieldName = edtName
    val fieldDescription = edtDescription

    init {
        initFields()
        disableFields()
    }

    fun enableFields() {
        edtName.isEnabled = true
        edtDescription.isEnabled = true
    }

    fun disableFields() {
        edtName.isEnabled = false
        edtDescription.isEnabled = false
    }

    fun clearFields() {
        edtName.text = ""
        edtDescription.text = ""
    }

    private fun initFields() {
        edtName.apply {
            bounds = Rectangle(10, 30, EDIT_NAME_WIDTH, EDIT_NAME_HEIGHT)
            background = EDIT_NAME_BACKGROUND
            foreground = COLOR_WHITE
            caretColor = COLOR_WHITE
            border = BorderFactory.createCompoundBorder(
                    TitledBorder(
                            LineBorder(BORDER_LINE_COLOR, 2, false),
                            "Наименование",
                            DEFAULT_JUSTIFICATION,
                            DEFAULT_POSITION,
                            Font(DEFAULT_FONT_NAME, Font.BOLD, 12),
                            COLOR_WHITE
                    ),
                    EmptyBorder(0, 4, 0, 0)
            )
        }

        edtDescription.apply {
            bounds = Rectangle(10, EDIT_NAME_HEIGHT + 30, EDIT_DESCRIPTION_WIDTH, EDIT_DESCRIPTION_HEIGHT)
            background = EDIT_DESCRIPTION_BACKGROUND
            foreground = COLOR_WHITE
            caretColor = COLOR_WHITE
            lineWrap = true
            border = BorderFactory.createCompoundBorder(
                    TitledBorder(
                            LineBorder(BORDER_LINE_COLOR, 2, false),
                            "Описание",
                            DEFAULT_JUSTIFICATION,
                            DEFAULT_POSITION,
                            Font(DEFAULT_FONT_NAME, Font.BOLD, 12),
                            COLOR_WHITE
                    ),
                    EmptyBorder(0, 4, 0, 0)
            )
        }
    }
}